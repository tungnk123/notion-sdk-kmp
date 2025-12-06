package io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.block.ParentDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common.CoverDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common.IconDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.richtext.RichTextDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataSourceDto(
    @SerialName("object") val objectType: String = "data_source",
    val id: String,
    val properties: Map<String, DataSourcePropertyDto> = emptyMap(),
    val parent: ParentDto? = null,
    @SerialName("database_parent") val databaseParent: ParentDto? = null,
    @SerialName("created_time") val createdTime: String? = null,
    @SerialName("created_by") val createdBy: PartialUser? = null,
    @SerialName("last_edited_time") val lastEditedTime: String? = null,
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