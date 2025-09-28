package core.data.model.internal.obj

import core.data.model.internal.obj.datasource.DataSourcePropertyObject
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataSourceObject(
    @SerialName("object") val objectType: String = "data_source",
    val id: String,
    val properties: Map<String, DataSourcePropertyObject> = emptyMap(),
    val parent: ParentObject? = null,
    @SerialName("database_parent") val databaseParent: ParentObject? = null,
    @SerialName("created_time") val createdTime: Instant? = null,
    @SerialName("created_by") val createdBy: PartialUser? = null,
    @SerialName("last_edited_time") val lastEditedTime: Instant? = null,
    @SerialName("last_edited_by") val lastEditedBy: PartialUser? = null,
    val title: List<RichTextObject>? = null,
    val description: List<RichTextObject>? = null,
    val icon: IconObject? = null,
    val cover: CoverObject? = null,
    val archived: Boolean? = null,
    @SerialName("is_inline") val isInline: Boolean? = null,
    val url: String? = null,
    @SerialName("in_trash") val inTrash: Boolean? = null
)

@Serializable
data class PartialUser(@SerialName("object") val objectType: String = "user", val id: String)

@Serializable
sealed class ParentObject {
    @Serializable @SerialName("database_id")
    data class DatabaseId(@SerialName("database_id") val databaseId: String) : ParentObject()
    @Serializable @SerialName("page_id")
    data class PageId(@SerialName("page_id") val pageId: String) : ParentObject()
}

@Serializable
sealed class CoverObject {
    @Serializable @SerialName("file")
    data class File(val file: FileRef) : CoverObject()
    @Serializable @SerialName("external")
    data class External(val external: ExternalFileRef) : CoverObject()
}

@Serializable
data class FileRef(val url: String, @SerialName("expiry_time") val expiryTime: String? = null)

@Serializable
data class ExternalFileRef(val url: String)

@Serializable
data class RichTextObject(
    val type: String,
    @SerialName("plain_text") val plainText: String? = null,
    val href: String? = null
)