package auth.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OAuthCreateTokenRequest(
    @SerialName("grant_type") val grantType: String = "authorization_code",
    val code: String,
    @SerialName("redirect_uri") val redirectUri: String? = null,
    @SerialName("code_verifier") val codeVerifier: String? = null
)

@Serializable
data class OAuthRefreshTokenRequest(
    @SerialName("grant_type") val grantType: String = "refresh_token",
    @SerialName("refresh_token") val refreshToken: String
)

@Serializable
data class OAuthRevokeRequest(
    val token: String
)

@Serializable
data class OAuthIntrospectRequest(
    val token: String
)
