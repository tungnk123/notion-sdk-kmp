package auth.repository

import auth.tokenstorage.TokenStorage
import auth.model.OAuthCreateTokenRequest
import auth.model.OAuthIntrospectRequest
import auth.model.OAuthIntrospectResponse
import auth.model.OAuthRefreshTokenRequest
import auth.model.OAuthRevokeRequest
import auth.model.OAuthTokenResponse
import auth.service.AuthService

class AuthRepository(
    private val service: AuthService,
    private val clientId: String,
    private val clientSecret: String,
    private val storage: TokenStorage
) {
    fun authorizeUrl(redirectUri: String, state: String, ownerWorkspace: Boolean = false): String =
        service.buildAuthorizeUrl(
            clientId = clientId,
            redirectUri = redirectUri,
            state = state,
            owner = if (ownerWorkspace) AuthService.Owner.Workspace else AuthService.Owner.User
        )

    suspend fun exchange(code: String, redirectUri: String? = null): OAuthTokenResponse {
        val resp = service.exchangeCodeBasic(
            clientId, clientSecret, OAuthCreateTokenRequest(code = code, redirectUri = redirectUri)
        )
        storage.set(resp)
        return resp
    }

    suspend fun refresh(): OAuthTokenResponse {
        val current = requireNotNull(storage.get())
        val rt = requireNotNull(current.refreshToken)
        val resp = service.refreshBasic(clientId, clientSecret, OAuthRefreshTokenRequest(refreshToken = rt))
        storage.set(resp)
        return resp
    }

    suspend fun revoke() {
        val access = storage.get()?.accessToken ?: return
        service.revokeBasic(clientId, clientSecret, OAuthRevokeRequest(token = access))
        storage.clear()
    }

    suspend fun introspect(): OAuthIntrospectResponse {
        val access = requireNotNull(storage.get()?.accessToken)
        return service.introspectBasic(clientId, clientSecret, OAuthIntrospectRequest(token = access))
    }

    fun accessTokenOrNull(): String? = storage.get()?.accessToken

    fun saveToken(token: OAuthTokenResponse) {
        storage.set(token)
    }
}