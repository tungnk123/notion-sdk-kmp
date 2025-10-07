package auth.service

import auth.model.*

interface AuthService {
    fun buildAuthorizeUrl(
        clientId: String, redirectUri: String, state: String, owner: Owner = Owner.User
    ): String

    suspend fun exchangeCodeBasic(
        clientId: String, clientSecret: String, req: OAuthCreateTokenRequest
    ): OAuthTokenResponse

    suspend fun refreshBasic(
        clientId: String, clientSecret: String, req: OAuthRefreshTokenRequest
    ): OAuthTokenResponse

    suspend fun revokeBasic(
        clientId: String, clientSecret: String, req: OAuthRevokeRequest
    )

    suspend fun introspectBasic(
        clientId: String, clientSecret: String, req: OAuthIntrospectRequest
    ): OAuthIntrospectResponse

    enum class Owner { User, Workspace }
}
