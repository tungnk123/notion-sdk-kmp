package auth.repository

import auth.tokenstorage.MultiTokenStorage
import auth.tokenstorage.TokenStorage
import auth.tokenstorage.WorkspaceTokenStorage
import auth.service.AuthService
import auth.model.OAuthTokenResponse

class AuthRepositoryFactory(
    private val service: AuthService,
    private val clientId: String,
    private val clientSecret: String,
    private val multi: MultiTokenStorage
) {
    fun getAuthRepository(): AuthRepository = AuthRepository(service, clientId, clientSecret, object : TokenStorage {
        private var authTokenResponse: OAuthTokenResponse? = null
        override fun get(): OAuthTokenResponse? = authTokenResponse
        override fun set(token: OAuthTokenResponse) { authTokenResponse = token }
        override fun clear() { authTokenResponse = null }
    })

    fun forWorkspace(workspaceId: String): AuthRepository =
        AuthRepository(service, clientId, clientSecret, WorkspaceTokenStorage(multi, workspaceId))
}
