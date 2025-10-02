package core.data.model.internal.request.block

import core.data.model.internal.dto.block.BlockDto
import kotlinx.serialization.Serializable

@Serializable
data class AppendBlockChildrenRequest(
    val children: List<BlockDto>,
    val after: String? = null
)
