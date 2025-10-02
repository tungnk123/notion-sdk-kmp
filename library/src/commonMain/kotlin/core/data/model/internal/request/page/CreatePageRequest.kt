package core.data.model.internal.request.page

import core.data.model.internal.dto.block.BlockDto
import core.data.model.internal.dto.block.ParentDto
import core.data.model.internal.dto.common.CoverDto
import core.data.model.internal.dto.common.IconDto
import core.data.model.internal.dto.page.PagePropertyDto
import kotlinx.serialization.Serializable

@Serializable
data class CreatePageRequest(
    val parent: ParentDto,
    val properties: Map<String, PagePropertyDto>,
    val children: List<BlockDto>? = null,
    val icon: IconDto? = null,
    val cover: CoverDto? = null,
)