package io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.block.ParentDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common.CoverDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common.IconDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.richtext.RichTextDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDatabaseRequest(
    val parent: ParentDto? = null,
    val title: List<RichTextDto>? = null,
    val description: List<RichTextDto>? = null,
    val icon: IconDto? = null,
    val cover: CoverDto? = null,
    @SerialName("is_inline") val isInline: Boolean? = null,
    val archived: Boolean? = null,
    @SerialName("in_trash") val inTrash: Boolean? = null
)
