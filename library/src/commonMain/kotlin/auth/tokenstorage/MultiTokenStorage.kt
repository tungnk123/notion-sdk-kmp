package auth.tokenstorage

import auth.model.OAuthTokenResponse

interface MultiTokenStorage {
    fun getCurrent(): OAuthTokenResponse?
    fun currentWorkspaceId(): String?
    fun get(workspaceId: String): OAuthTokenResponse?
    fun set(workspaceId: String, token: OAuthTokenResponse, makeCurrent: Boolean = true)
    fun setCurrent(workspaceId: String?)
    fun remove(workspaceId: String)
    fun all(): Map<String, OAuthTokenResponse>
    fun clear()
}

class InMemoryMultiTokenStorage : MultiTokenStorage {
    private val map = linkedMapOf<String, OAuthTokenResponse>()
    private var current: String? = null
    override fun getCurrent(): OAuthTokenResponse? = current?.let { map[it] }
    override fun currentWorkspaceId(): String? = current
    override fun get(workspaceId: String): OAuthTokenResponse? = map[workspaceId]
    override fun set(workspaceId: String, token: OAuthTokenResponse, makeCurrent: Boolean) {
        map[workspaceId] = token
        if (makeCurrent) current = workspaceId
    }

    override fun setCurrent(workspaceId: String?) {
        current = workspaceId?.takeIf { map.containsKey(it) }
    }

    override fun remove(workspaceId: String) {
        map.remove(workspaceId)
        if (current == workspaceId) current = map.keys.firstOrNull()
    }

    override fun all(): Map<String, OAuthTokenResponse> = map.toMap()
    override fun clear() {
        map.clear()
        current = null
    }
}
