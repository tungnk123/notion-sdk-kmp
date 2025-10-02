package core.data.model.internal.dto.datasource

import core.data.model.internal.dto.IconDto
import core.data.model.internal.dto.richtext.RichTextDto
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataSourceDto(
    @SerialName("object") val objectType: String = "data_source",
    val id: String,
    val properties: Map<String, DataSourcePropertyDto> = emptyMap(),
    val parent: ParentDto? = null,
    @SerialName("database_parent") val databaseParent: ParentDto? = null,
    @SerialName("created_time") val createdTime: Instant? = null,
    @SerialName("created_by") val createdBy: PartialUser? = null,
    @SerialName("last_edited_time") val lastEditedTime: Instant? = null,
    @SerialName("last_edited_by") val lastEditedBy: PartialUser? = null,
    val title: List<RichTextDto>? = null,
    val description: List<RichTextDto>? = null,
    val icon: IconDto? = null,
    val cover: CoverDto? = null,
    val archived: Boolean? = null,
    @SerialName("is_inline") val isInline: Boolean? = null,
    val url: String? = null,
    @SerialName("in_trash") val inTrash: Boolean? = null
)

@Serializable
data class PartialUser(@SerialName("object") val objectType: String = "user", val id: String)

@Serializable
public sealed class ParentDto {
    @Serializable
    @SerialName("database_id")
    data class DatabaseId(
        @SerialName("database_id") val databaseId: String
    ) : ParentDto()

    @Serializable
    @SerialName("data_source_id")
    data class DataSourceId(
        @SerialName("data_source_id") val dataSourceId: String
    ) : ParentDto()

    @Serializable
    @SerialName("page_id")
    data class PageId(
        @SerialName("page_id") val pageId: String
    ) : ParentDto()

    @Serializable
    @SerialName("block_id")
    data class BlockId(
        @SerialName("block_id") val blockId: String
    ) : ParentDto()

    @Serializable
    @SerialName("workspace")
    data class Workspace(
        @SerialName("workspace") val workspace: Boolean = true
    ) : ParentDto()
}

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