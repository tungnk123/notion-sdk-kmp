package core.data.model.internal.dto.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class IconDto {
    @Serializable
    @SerialName("emoji")
    data class Emoji(
        @SerialName("emoji") val emoji: String
    ) : IconDto()

    @Serializable
    @SerialName("external")
    data class External(
        val external: ExternalValue
    ) : IconDto() {
        @Serializable
        data class ExternalValue(val url: String)
    }

    @Serializable
    @SerialName("file")
    data class File(
        val file: FileValue
    ) : IconDto() {
        @Serializable
        data class FileValue(
            val url: String,
            @SerialName("expiry_time") val expiryTime: String
        )
    }

    @Serializable
    @SerialName("custom_emoji")
    data class CustomEmoji(
        @SerialName("custom_emoji") val customEmoji: Value
    ) : IconDto() {
        @Serializable
        data class Value(
            val id: String? = null,
            val name: String? = null,
            val url: String? = null,
            val emoji: String? = null
        )
    }
}