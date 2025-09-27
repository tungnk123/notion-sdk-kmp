package core.data.model.result

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class NotionFile {
    abstract val url: String

    @Serializable
    @SerialName("file")
    data class File(
        override val url: String,
        @SerialName("expiry_time") val expiryTime: String,
    ) : NotionFile()

    @Serializable
    @SerialName("external")
    data class External(
        override val url: String,
    ) : NotionFile()
}
