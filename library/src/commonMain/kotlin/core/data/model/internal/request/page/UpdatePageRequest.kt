package core.data.model.internal.request.page

import core.data.model.internal.dto.IconDto
import core.data.model.internal.dto.datasource.CoverDto
import core.data.model.internal.dto.page.PagePropertyDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePageRequest(
    val properties: Map<String, PagePropertyDto>? = null,
    val icon: IconDto? = null,
    val cover: CoverDto? = null,
    val archived: Boolean? = null
)