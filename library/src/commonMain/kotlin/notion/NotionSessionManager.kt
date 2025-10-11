package notion

import auth.repository.AuthRepositoryFactory
import auth.tokenstorage.MultiTokenStorage
import io.ktor.http.Url
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import notion.model.AuthIntent
import notion.model.NotionSession
import notion.model.NotionSessionMeta
import notion.model.SessionState

class NotionSessionManager(
    private val authFactory: AuthRepositoryFactory, private val tokenStore: MultiTokenStorage
) {
    private val sessionsMap = linkedMapOf<String, NotionSession>()
    private val mutex = Mutex()
    private val _state = MutableStateFlow<SessionState>(SessionState.SignedOut)
    val state: StateFlow<SessionState> = _state.asStateFlow()

    fun beginAuthorization(redirectUri: String, state: String, ownerWorkspace: Boolean = false): AuthIntent {
        val url = authFactory.getAuthRepository().authorizeUrl(redirectUri, state, ownerWorkspace)
        val intent = AuthIntent(url, state)
        _state.value = SessionState.Authorizing(intent)
        return intent
    }

    suspend fun handleOAuthRedirect(callbackUrl: String, expectedState: String): NotionSession = mutex.withLock {
        val parsed = Url(callbackUrl)
        val code = parsed.parameters["code"] ?: error("missing code")
        val state = parsed.parameters["state"] ?: error("missing state")
        require(state == expectedState)

        val tempRepo = authFactory.getAuthRepository()
        val token = tempRepo.exchange(code, "${parsed.protocol.name}://${parsed.host}${parsed.encodedPath}")
        val workspaceId = token.workspaceId ?: error("missing workspace_id")

        val repo = authFactory.forWorkspace(workspaceId)
        repo.saveToken(token)

        val client = NotionClientFactory.fromAuthRepository(repo)
        val session = NotionSession(workspaceId, token.workspaceName, token, client, repo)
        sessionsMap[workspaceId] = session
        tokenStore.setCurrent(workspaceId)
        updateState()
        session
    }

    suspend fun initialize(): NotionSession? = mutex.withLock {
        val id = tokenStore.currentWorkspaceId() ?: tokenStore.all().keys.firstOrNull() ?: return null
        val session = sessionsMap[id] ?: loadSessionFromStore(id) ?: return null
        updateState()
        session
    }

    fun currentSession(): NotionSession? {
        val id = tokenStore.currentWorkspaceId() ?: return null
        return sessionsMap[id] ?: loadSessionFromStore(id)
    }

    fun listSessions(): List<NotionSessionMeta> {
        val cached =
            sessionsMap.values.map { NotionSessionMeta(it.workspaceId, it.workspaceName, it.token.workspaceIcon) }
        val stored = tokenStore.all().map { (k, v) -> NotionSessionMeta(k, v.workspaceName, v.workspaceIcon) }
        val map = linkedMapOf<String, NotionSessionMeta>()
        cached.forEach { map[it.workspaceId] = it }
        stored.forEach { map[it.workspaceId] = it }
        return map.values.toList()
    }

    suspend fun switchWorkspace(workspaceId: String): NotionSession = mutex.withLock {
        tokenStore.setCurrent(workspaceId)
        val session = sessionsMap[workspaceId] ?: loadSessionFromStore(workspaceId)
        requireNotNull(session)
        updateState()
        session
    }

    suspend fun disconnectWorkspace(workspaceId: String? = tokenStore.currentWorkspaceId()) = mutex.withLock {
        val id = workspaceId ?: return
        runCatching { sessionsMap[id]?.auth?.revoke() }
        sessionsMap.remove(id)
        tokenStore.remove(id)
        if (tokenStore.currentWorkspaceId() == null) _state.value = SessionState.SignedOut else updateState()
    }

    private fun loadSessionFromStore(workspaceId: String): NotionSession? {
        val token = tokenStore.get(workspaceId) ?: return null
        val repo = authFactory.forWorkspace(workspaceId)
        val client = NotionClientFactory.fromAuthRepository(repo)
        val session = NotionSession(workspaceId, token.workspaceName, token, client, repo)
        sessionsMap[workspaceId] = session
        return session
    }

    private fun updateState() {
        val cur = currentSession()
        _state.value = cur?.let { SessionState.Authorized(it, listSessions()) } ?: SessionState.SignedOut
    }
}