package core.data.model.result.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class NotionIcon {
    @Serializable
    @SerialName("emoji")
    data class Emoji(val emoji: String) : NotionIcon()

    @Serializable
    @SerialName("file")
    data class File(
        val url: String, @SerialName("expiry_time") val expiryTime: String? = null
    ) : NotionIcon()

    @Serializable
    @SerialName("external")
    data class External(val url: String) : NotionIcon()
}