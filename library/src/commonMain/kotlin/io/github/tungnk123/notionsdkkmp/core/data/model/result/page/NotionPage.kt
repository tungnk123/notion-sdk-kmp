package io.github.tungnk123.notionsdkkmp.core.data.model.result.page

import io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionIcon
import io.github.tungnk123.notionsdkkmp.core.data.model.result.user.NotionPartialUser

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
