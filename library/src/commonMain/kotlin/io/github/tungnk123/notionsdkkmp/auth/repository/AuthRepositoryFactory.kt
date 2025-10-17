package io.github.tungnk123.notionsdkkmp.auth.repository

import io.github.tungnk123.notionsdkkmp.auth.tokenstorage.MultiTokenStorage
import io.github.tungnk123.notionsdkkmp.auth.tokenstorage.TokenStorage
import io.github.tungnk123.notionsdkkmp.auth.tokenstorage.WorkspaceTokenStorage
import io.github.tungnk123.notionsdkkmp.auth.service.AuthService
import io.github.tungnk123.notionsdkkmp.auth.model.OAuthTokenResponse

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
