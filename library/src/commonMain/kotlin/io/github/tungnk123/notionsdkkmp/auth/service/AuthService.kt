package io.github.tungnk123.notionsdkkmp.auth.service

import io.github.tungnk123.notionsdkkmp.auth.model.OAuthCreateTokenRequest
import io.github.tungnk123.notionsdkkmp.auth.model.OAuthIntrospectRequest
import io.github.tungnk123.notionsdkkmp.auth.model.OAuthIntrospectResponse
import io.github.tungnk123.notionsdkkmp.auth.model.OAuthRefreshTokenRequest
import io.github.tungnk123.notionsdkkmp.auth.model.OAuthRevokeRequest
import io.github.tungnk123.notionsdkkmp.auth.model.OAuthTokenResponse

interface AuthService {
    fun buildAuthorizeUrl(
        clientId: String, redirectUri: String, state: String, owner: Owner = Owner.User
    ): String

    suspend fun exchangeCodeBasic(
        clientId: String, clientSecret: String, request: OAuthCreateTokenRequest
    ): OAuthTokenResponse

    suspend fun refreshBasic(
        clientId: String, clientSecret: String, request: OAuthRefreshTokenRequest
    ): OAuthTokenResponse

    suspend fun revokeBasic(
        clientId: String, clientSecret: String, request: OAuthRevokeRequest
    )

    suspend fun introspectBasic(
        clientId: String, clientSecret: String, request: OAuthIntrospectRequest
    ): OAuthIntrospectResponse

    enum class Owner { User, Workspace }
}
