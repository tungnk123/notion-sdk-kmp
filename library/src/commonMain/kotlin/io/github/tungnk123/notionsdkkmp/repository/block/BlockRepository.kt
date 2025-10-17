package io.github.tungnk123.notionsdkkmp.repository.block

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.block.BlockDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.block.AppendBlockChildrenRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.response.ResultsResponseDto
import io.github.tungnk123.notionsdkkmp.core.data.model.result.block.NotionBlock

interface BlockRepository {
    suspend fun retrieve(blockId: String): NotionBlock
    suspend fun update(blockId: String, request: BlockDto): NotionBlock
    suspend fun delete(blockId: String): NotionBlock
    suspend fun listChildren(
        blockId: String, startCursor: String? = null, pageSize: Int? = null
    ): ResultsResponseDto<BlockDto>

    suspend fun appendChildren(blockId: String, request: AppendBlockChildrenRequest): ResultsResponseDto<BlockDto>
}