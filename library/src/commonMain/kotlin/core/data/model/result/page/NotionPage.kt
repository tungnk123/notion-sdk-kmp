package core.data.model.result.page

import core.data.model.result.NotionIcon
import core.data.model.result.NotionUser
import core.data.model.result.common.NotionPartialUser

data class NotionPage(
    val id: String,
    val createdTime: String,
    val lastEditedTime: String,
    val createdBy: NotionPartialUser,
    val lastEditedBy: NotionPartialUser,
    val cover: NotionIcon?,
    val icon: NotionIcon?,
    val parentId: String,
    val archived: Boolean,
    val inTrash: Boolean,
    val properties: Map<String, NotionPageProperty>,
    val url: String,
    val publicUrl: String?,
)
