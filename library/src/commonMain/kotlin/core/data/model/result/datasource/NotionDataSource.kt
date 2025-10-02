package core.data.model.result.datasource

import core.data.model.result.common.NotionIcon
import core.data.model.result.common.NotionCover
import core.data.model.result.common.NotionParent
import core.data.model.result.user.NotionPartialUser
import core.data.model.result.richtext.NotionRichText
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NotionDataSource(
    val id: String,
    val properties: Map<String, NotionDataSourceProperty> = emptyMap(),
    val parent: NotionParent? = null,
    @SerialName("database_parent") val databaseParent: NotionParent? = null,
    @SerialName("created_time") val createdTime: Instant? = null,
    @SerialName("created_by") val createdBy: NotionPartialUser? = null,
    @SerialName("last_edited_time") val lastEditedTime: Instant? = null,
    @SerialName("last_edited_by") val lastEditedBy: NotionPartialUser? = null,
    val title: List<NotionRichText> = emptyList(),
    val description: List<NotionRichText> = emptyList(),
    val icon: NotionIcon? = null,
    val cover: NotionCover? = null,
    val archived: Boolean = false,
    @SerialName("is_inline") val isInline: Boolean = false,
    val url: String? = null,
    @SerialName("in_trash") val inTrash: Boolean = false
)
