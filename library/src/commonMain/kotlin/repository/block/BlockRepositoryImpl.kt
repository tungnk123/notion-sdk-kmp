package repository.block

import core.data.mapper.toDomain
import core.data.model.internal.dto.block.BlockDto
import core.data.model.internal.request.block.AppendBlockChildrenRequest
import core.data.model.internal.response.ResultsResponseDto
import core.data.model.result.block.NotionBlock
import service.block.BlockService

class BlockRepositoryImpl(
    private val service: BlockService
) : BlockRepository {

    override suspend fun retrieve(blockId: String): NotionBlock = service.retrieve(blockId).toDomain()

    override suspend fun update(blockId: String, request: BlockDto): NotionBlock =
        service.update(blockId, request).toDomain()

    override suspend fun delete(blockId: String): NotionBlock = service.delete(blockId).toDomain()

    override suspend fun listChildren(
        blockId: String, startCursor: String?, pageSize: Int?
    ): ResultsResponseDto<BlockDto> = service.listChildren(blockId, startCursor, pageSize)

    override suspend fun appendChildren(
        blockId: String, request: AppendBlockChildrenRequest
    ): ResultsResponseDto<BlockDto> = service.appendChildren(blockId, request)
}
