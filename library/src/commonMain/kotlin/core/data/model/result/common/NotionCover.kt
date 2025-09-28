package core.data.model.result.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class NotionCover {
    @Serializable @SerialName("file")
    data class File(val url: String, @SerialName("expiry_time") val expiryTime: String? = null) : NotionCover()
    @Serializable @SerialName("external")
    data class External(val url: String) : NotionCover()
}
