package io.github.tungnk123.notionsdkkmp.service.block

import core.data.model.internal.dto.block.BlockDto
import core.data.model.internal.request.block.AppendBlockChildrenRequest
import core.data.model.internal.response.ResultsResponseDto

interface BlockService {
    suspend fun retrieve(blockId: String): BlockDto
    suspend fun update(blockId: String, request: BlockDto): BlockDto
    suspend fun delete(blockId: String): BlockDto

    suspend fun listChildren(
        blockId: String, startCursor: String? = null, pageSize: Int? = null
    ): ResultsResponseDto<BlockDto>

    suspend fun appendChildren(
        blockId: String, request: AppendBlockChildrenRequest
    ): ResultsResponseDto<BlockDto>
}
