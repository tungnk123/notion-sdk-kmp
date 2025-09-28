package core.data.model.internal.dto.page

import core.data.model.internal.dto.IconDto
import core.data.model.internal.dto.page.PagePropertyDto
import kotlinx.serialization.Serializable

@Serializable
internal data class PageDto(
    val id: String,
    val url: String,
    val properties: Map<String, PagePropertyDto>,
    val iconDto: IconDto? = null,
)