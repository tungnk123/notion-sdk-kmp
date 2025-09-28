package core.data.model.internal.dto

import core.data.model.internal.dto.datasource.ExternalFileRef
import core.data.model.internal.dto.datasource.FileRef
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class IconDto {
    @Serializable @SerialName("emoji")
    data class Emoji(val emoji: String) : IconDto()
    @Serializable @SerialName("file")
    data class File(val file: FileRef) : IconDto()
    @Serializable @SerialName("external")
    data class External(val external: ExternalFileRef) : IconDto()
}