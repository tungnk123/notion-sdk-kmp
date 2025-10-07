package auth.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class OAuthTokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String? = null,
    @SerialName("token_type") val tokenType: String? = null,
    @SerialName("bot_id") val botId: String? = null,
    @SerialName("workspace_id") val workspaceId: String? = null,
    @SerialName("workspace_name") val workspaceName: String? = null,
    @SerialName("workspace_icon") val workspaceIcon: String? = null,
    val owner: JsonObject? = null,
    @SerialName("duplicated_template_id") val duplicatedTemplateId: String? = null,
    @SerialName("expires_in") val expiresIn: Long? = null,
    val scope: String? = null
)

@Serializable
data class OAuthIntrospectResponse(
    val active: Boolean,
    val scope: String? = null,
    @SerialName("client_id") val clientId: String? = null,
    @SerialName("token_type") val tokenType: String? = null,
    val exp: Long? = null,
    val iat: Long? = null,
    val nbf: Long? = null,
    val sub: String? = null,
    val aud: String? = null,
    val iss: String? = null
)
