package auth.service

import auth.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlin.io.encoding.Base64

private object OAuthRoutes {
    const val AUTHORIZE = "https://api.notion.com/v1/oauth/authorize"
    const val TOKEN = "https://api.notion.com/v1/oauth/token"
    const val REVOKE = "https://api.notion.com/v1/oauth/revoke"
    const val INTROSPECT = "https://api.notion.com/v1/oauth/introspect"
}

class AuthServiceImpl(
    private val httpClient: HttpClient
) : AuthService {

    override fun buildAuthorizeUrl(
        clientId: String, redirectUri: String, state: String, owner: AuthService.Owner
    ): String {
        val ownerParam = when (owner) {
            AuthService.Owner.User -> "user"
            AuthService.Owner.Workspace -> "workspace"
        }
        val url = URLBuilder(OAuthRoutes.AUTHORIZE)
        url.parameters.append("client_id", clientId)
        url.parameters.append("response_type", "code")
        url.parameters.append("redirect_uri", redirectUri)
        url.parameters.append("state", state)
        url.parameters.append("owner", ownerParam)
        return url.buildString()
    }

    override suspend fun exchangeCodeBasic(
        clientId: String, clientSecret: String, req: OAuthCreateTokenRequest
    ): OAuthTokenResponse {
        val basic = basicAuth(clientId, clientSecret)
        return httpClient.post(OAuthRoutes.TOKEN) {
            headers {
                append(HttpHeaders.Authorization, "Basic $basic")
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            setBody(req)
        }.body()
    }

    override suspend fun refreshBasic(
        clientId: String, clientSecret: String, req: OAuthRefreshTokenRequest
    ): OAuthTokenResponse {
        val basic = basicAuth(clientId, clientSecret)
        return httpClient.post(OAuthRoutes.TOKEN) {
            headers {
                append(HttpHeaders.Authorization, "Basic $basic")
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            setBody(req)
        }.body()
    }

    override suspend fun revokeBasic(
        clientId: String, clientSecret: String, req: OAuthRevokeRequest
    ) {
        val basic = basicAuth(clientId, clientSecret)
        httpClient.post(OAuthRoutes.REVOKE) {
            headers {
                append(HttpHeaders.Authorization, "Basic $basic")
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            setBody(req)
        }
    }

    override suspend fun introspectBasic(
        clientId: String, clientSecret: String, req: OAuthIntrospectRequest
    ): OAuthIntrospectResponse {
        val basic = basicAuth(clientId, clientSecret)
        return httpClient.post(OAuthRoutes.INTROSPECT) {
            headers {
                append(HttpHeaders.Authorization, "Basic $basic")
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            setBody(req)
        }.body()
    }

    private fun basicAuth(clientId: String, clientSecret: String): String {
        val bytes = "$clientId:$clientSecret".encodeToByteArray()
        return Base64.encode(bytes)
    }
}