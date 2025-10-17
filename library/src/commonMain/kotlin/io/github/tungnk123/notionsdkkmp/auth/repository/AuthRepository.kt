package io.github.tungnk123.notionsdkkmp.auth.repository

import io.github.tungnk123.notionsdkkmp.auth.tokenstorage.TokenStorage
import io.github.tungnk123.notionsdkkmp.auth.model.OAuthCreateTokenRequest
import io.github.tungnk123.notionsdkkmp.auth.model.OAuthIntrospectRequest
import io.github.tungnk123.notionsdkkmp.auth.model.OAuthIntrospectResponse
import io.github.tungnk123.notionsdkkmp.auth.model.OAuthRefreshTokenRequest
import io.github.tungnk123.notionsdkkmp.auth.model.OAuthRevokeRequest
import io.github.tungnk123.notionsdkkmp.auth.model.OAuthTokenResponse
import io.github.tungnk123.notionsdkkmp.auth.service.AuthService

class AuthRepository(
    private val service: AuthService,
    private val clientId: String,
    private val clientSecret: String,
    private val storage: TokenStorage
) {
    companion object {
        private const val ERROR_NO_TOKEN = "No authentication token available"
        private const val ERROR_NO_REFRESH_TOKEN = "No refresh token available"
    }

    fun authorizeUrl(
        redirectUri: String,
        state: String,
        ownerWorkspace: Boolean = false
    ): String = service.buildAuthorizeUrl(
        clientId = clientId,
        redirectUri = redirectUri,
        state = state,
        owner = if (ownerWorkspace) AuthService.Owner.Workspace else AuthService.Owner.User
    )

    suspend fun exchangeCodeForTokenAndSaveToken(code: String, redirectUri: String): OAuthTokenResponse =
        service.exchangeCodeBasic(
            clientId = clientId,
            clientSecret = clientSecret,
            request = OAuthCreateTokenRequest(code = code, redirectUri = redirectUri)
        ).also(storage::set)

    suspend fun refresh(): OAuthTokenResponse {
        val refreshToken = getCurrentToken()?.refreshToken
            ?: throw IllegalStateException(ERROR_NO_REFRESH_TOKEN)

        return service.refreshBasic(
            clientId = clientId,
            clientSecret = clientSecret,
            request = OAuthRefreshTokenRequest(refreshToken = refreshToken)
        ).also(storage::set)
    }

    suspend fun revoke() {
        val accessToken = storage.get()?.accessToken ?: return

        runCatching {
            service.revokeBasic(
                clientId = clientId,
                clientSecret = clientSecret,
                request = OAuthRevokeRequest(token = accessToken)
            )
        }
        storage.clear()
    }

    suspend fun introspect(): OAuthIntrospectResponse {
        val accessToken = getCurrentToken()?.accessToken
            ?: throw IllegalStateException(ERROR_NO_TOKEN)

        return service.introspectBasic(
            clientId = clientId,
            clientSecret = clientSecret,
            request = OAuthIntrospectRequest(token = accessToken)
        )
    }

    fun getAccessToken(): String? = storage.get()?.accessToken

    fun saveToken(token: OAuthTokenResponse) {
        storage.set(token)
    }

    private fun getCurrentToken(): OAuthTokenResponse? = storage.get()
}