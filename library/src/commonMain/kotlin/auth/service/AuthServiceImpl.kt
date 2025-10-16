package auth.service

import auth.model.*
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

private object OAuthRoutes {
    const val AUTHORIZE = "https://api.notion.com/v1/oauth/authorize"
    const val TOKEN = "https://api.notion.com/v1/oauth/token"
    const val REVOKE = "https://api.notion.com/v1/oauth/revoke"
    const val INTROSPECT = "https://api.notion.com/v1/oauth/introspect"
}

class AuthServiceImpl(
    private val httpClient: HttpClient
) : AuthService {

    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
    }

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
        clientId: String, clientSecret: String, request: OAuthCreateTokenRequest
    ): OAuthTokenResponse {
        val basic = basicAuth(clientId, clientSecret)
        val requestBody = json.encodeToString(OAuthCreateTokenRequest.serializer(), request)
        val response: HttpResponse = try {
            httpClient.post(OAuthRoutes.TOKEN) {
                headers {
                    append(HttpHeaders.Authorization, "Basic $basic")
                }
                contentType(ContentType.Application.Json)
                setBody(requestBody)
            }
        } catch (e: Exception) {
            println("ERROR: Request failed - ${e.message}")
            e.printStackTrace()
            throw e
        }

        val bodyText = try {
            response.bodyAsText()
        } catch (e: Exception) {
            println("ERROR: Failed to read response body - ${e.message}")
            e.printStackTrace()
            throw e
        }


        if (bodyText.isEmpty() || bodyText.isBlank()) {
            error("Empty response body from OAuth token endpoint. Status: ${response.status}, Headers: ${response.headers.entries()}")
        }

        return try {
            json.decodeFromString(OAuthTokenResponse.serializer(), bodyText)
        } catch (e: Exception) {
            println("ERROR: Failed to parse JSON - ${e.message}")
            throw e
        }
    }

    override suspend fun refreshBasic(
        clientId: String, clientSecret: String, request: OAuthRefreshTokenRequest
    ): OAuthTokenResponse {
        val basic = basicAuth(clientId, clientSecret)
        val response: HttpResponse = httpClient.post(OAuthRoutes.TOKEN) {
            headers {
                append(HttpHeaders.Authorization, "Basic $basic")
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        val bodyText = response.bodyAsText()
        return json.decodeFromString(OAuthTokenResponse.serializer(), bodyText)
    }

    override suspend fun revokeBasic(
        clientId: String, clientSecret: String, request: OAuthRevokeRequest
    ) {
        val basic = basicAuth(clientId, clientSecret)
        httpClient.post(OAuthRoutes.REVOKE) {
            headers {
                append(HttpHeaders.Authorization, "Basic $basic")
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun introspectBasic(
        clientId: String, clientSecret: String, request: OAuthIntrospectRequest
    ): OAuthIntrospectResponse {
        val basic = basicAuth(clientId, clientSecret)
        val response: HttpResponse = httpClient.post(OAuthRoutes.INTROSPECT) {
            headers {
                append(HttpHeaders.Authorization, "Basic $basic")
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        val bodyText = response.bodyAsText()
        return json.decodeFromString(OAuthIntrospectResponse.serializer(), bodyText)
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun basicAuth(clientId: String, clientSecret: String): String {
        val bytes = "$clientId:$clientSecret".encodeToByteArray()
        return Base64.encode(bytes)
    }
}