package io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.database

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.block.ParentDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common.CoverDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common.IconDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.PartialUser
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.richtext.RichTextDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DatabaseDto(
    @SerialName("object") val objectType: String = "database",
    val id: String,
    @SerialName("data_sources") val dataSources: List<ChildDataSourceRef> = emptyList(),
    @SerialName("created_time") val createdTime: String? = null,
    @SerialName("created_by") val createdBy: PartialUser? = null,
    @SerialName("last_edited_time") val lastEditedTime: String? = null,
    @SerialName("last_edited_by") val lastEditedBy: PartialUser? = null,
    val title: List<RichTextDto>? = null,
    val description: List<RichTextDto>? = null,
    val icon: IconDto? = null,
    val cover: CoverDto? = null,
    val parent: ParentDto? = null,
    val url: String? = null,
    val archived: Boolean? = null,
    @SerialName("in_trash") val inTrash: Boolean? = null,
    @SerialName("is_inline") val isInline: Boolean? = null,
    @SerialName("public_url") val publicUrl: String? = null
)

@Serializable
data class ChildDataSourceRef(
    val id: String,
    val name: String? = null
)