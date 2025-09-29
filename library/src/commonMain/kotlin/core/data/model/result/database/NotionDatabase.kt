package core.data.model.result.database

import core.data.model.result.NotionIcon
import core.data.model.result.common.NotionCover
import core.data.model.result.common.NotionParent
import core.data.model.result.common.NotionPartialUser
import core.data.model.result.richtext.NotionRichText
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class NotionDatabase(
    val id: String,
    val dataSources: List<ChildDataSource> = emptyList(),
    val createdTime: Instant? = null,
    val createdBy: NotionPartialUser? = null,
    val lastEditedTime: Instant? = null,
    val lastEditedBy: NotionPartialUser? = null,
    val title: List<NotionRichText> = emptyList(),
    val description: List<NotionRichText> = emptyList(),
    val icon: NotionIcon? = null,
    val cover: NotionCover? = null,
    val parent: NotionParent? = null,
    val url: String? = null,
    val archived: Boolean = false,
    val inTrash: Boolean = false,
    val isInline: Boolean = false,
    val publicUrl: String? = null
)

@Serializable
data class ChildDataSource(
    val id: String,
    val name: String? = null
)