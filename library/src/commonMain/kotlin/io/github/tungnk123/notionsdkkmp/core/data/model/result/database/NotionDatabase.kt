package io.github.tungnk123.notionsdkkmp.core.data.model.result.database

import io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionCover
import io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionIcon
import io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionParent
import io.github.tungnk123.notionsdkkmp.core.data.model.result.richtext.NotionRichText
import io.github.tungnk123.notionsdkkmp.core.data.model.result.user.NotionPartialUser
import kotlinx.serialization.Serializable

@Serializable
data class NotionDatabase(
    val id: String,
    val dataSources: List<ChildDataSource> = emptyList(), val createdTime: String? = null,
    val createdBy: NotionPartialUser? = null, val lastEditedTime: String? = null,
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