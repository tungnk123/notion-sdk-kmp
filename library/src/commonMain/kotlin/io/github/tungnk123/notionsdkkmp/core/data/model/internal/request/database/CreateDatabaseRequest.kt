package io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.block.ParentDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common.CoverDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common.IconDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.richtext.RichTextDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateDatabaseRequest(
    val parent: ParentDto,
    @SerialName("initial_data_source")
    val initialDataSource: InitialDataSourceRequest,
    val title: List<RichTextDto>? = null,
    val description: List<RichTextDto>? = null,
    val icon: IconDto? = null,
    val cover: CoverDto? = null
)

@Serializable
data class InitialDataSourceRequest(
    val properties: Map<String, DataSourcePropertyDto>
)
