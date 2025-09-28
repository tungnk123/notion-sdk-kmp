package core.data.model.internal.request.datasource

import core.data.model.internal.dto.IconDto
import core.data.model.internal.dto.datasource.DataSourcePropertyDto
import core.data.model.internal.dto.datasource.ParentDto
import core.data.model.internal.dto.richtext.RichTextDto
import kotlinx.serialization.Serializable

@Serializable
data class CreateDataSourceRequest(
    val parent: ParentDto,
    val properties: Map<String, DataSourcePropertyDto>,
    val title: List<RichTextDto>? = null,
    val description: List<RichTextDto>? = null,
    val icon: IconDto? = null,
)