package io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class CoverDto {
    @Serializable
    @SerialName("file")
    data class File(val file: FileRef) : CoverDto()

    @Serializable
    @SerialName("external")
    data class External(val external: ExternalFileRef) : CoverDto()
}

@Serializable
data class FileRef(
    val url: String, @SerialName("expiry_time") val expiryTime: String? = null
)

@Serializable
data class ExternalFileRef(val url: String)