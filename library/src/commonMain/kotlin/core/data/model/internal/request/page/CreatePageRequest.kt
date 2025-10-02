package core.data.model.internal.request.page

import core.data.model.internal.dto.BlockDto
import core.data.model.internal.dto.IconDto
import core.data.model.internal.dto.datasource.CoverDto
import core.data.model.internal.dto.datasource.ParentDto
import core.data.model.internal.dto.page.PagePropertyDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePageRequest(
    val parent: ParentDto,
    val properties: Map<String, PagePropertyDto>,
    val children: List<BlockDto>? = null,
    val icon: IconDto? = null,
    val cover: CoverDto? = null,
)