package core.data.model.internal.request.datasource

import core.data.model.internal.dto.common.IconDto
import core.data.model.internal.dto.datasource.DataSourcePropertyDto
import core.data.model.internal.dto.richtext.RichTextDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDataSourceRequest(
    val properties: Map<String, DataSourcePropertyDto>? = null,
    val title: List<RichTextDto>? = null,
    val description: List<RichTextDto>? = null,
    val icon: IconDto? = null,
    val archived: Boolean? = null,
    @SerialName("in_trash") val inTrash: Boolean? = null
)
