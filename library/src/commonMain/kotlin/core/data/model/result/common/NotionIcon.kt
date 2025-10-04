package core.data.model.result.common

import kotlinx.serialization.Serializable

@Serializable
sealed class NotionIcon {
    @Serializable
    data class Emoji(val emoji: String) : NotionIcon()

    @Serializable
    data class External(val url: String) : NotionIcon()

    @Serializable
    data class File(val url: String, val expiryTime: String) : NotionIcon()

    @Serializable
    data class CustomEmoji(
        val id: String? = null,
        val name: String? = null,
        val url: String? = null,
        val emoji: String? = null
    ) : NotionIcon()
}