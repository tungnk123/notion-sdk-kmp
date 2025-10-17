package io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.datasource

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.block.ParentDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common.IconDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.richtext.RichTextDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateDataSourceRequest(
    val parent: ParentDto,
    val properties: Map<String, DataSourcePropertyDto>,
    val title: List<RichTextDto>? = null,
    val description: List<RichTextDto>? = null,
    val icon: IconDto? = null,
)