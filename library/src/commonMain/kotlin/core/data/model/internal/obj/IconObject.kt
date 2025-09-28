package core.data.model.internal.obj

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class IconObject {
    @Serializable @SerialName("emoji")
    data class Emoji(val emoji: String) : IconObject()
    @Serializable @SerialName("file")
    data class File(val file: FileRef) : IconObject()
    @Serializable @SerialName("external")
    data class External(val external: ExternalFileRef) : IconObject()
}