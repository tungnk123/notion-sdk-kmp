package io.github.tungnk123.notionsdkkmp.repository.block

import io.github.tungnk123.notionsdkkmp.core.data.mapper.toDomain
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.block.BlockDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.block.AppendBlockChildrenRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.response.ResultsResponseDto
import io.github.tungnk123.notionsdkkmp.core.data.model.result.block.NotionBlock
import io.github.tungnk123.notionsdkkmp.service.block.BlockService

class BlockRepositoryImpl(
    private val service: BlockService
) : BlockRepository {

    override suspend fun retrieve(blockId: String): NotionBlock =
        service.retrieve(blockId).toDomain()

    override suspend fun update(blockId: String, request: BlockDto): NotionBlock =
        service.update(blockId, request).toDomain()

    override suspend fun delete(blockId: String): NotionBlock = service.delete(blockId).toDomain()

    override suspend fun listChildren(
        blockId: String, startCursor: String?, pageSize: Int?
    ): ResultsResponseDto<BlockDto> = service.listChildren(blockId, startCursor, pageSize)

    override suspend fun appendChildren(
        blockId: String, request: AppendBlockChildrenRequest
    ): ResultsResponseDto<BlockDto> = service.appendChildren(blockId, request)

    override suspend fun getAllChildren(blockId: String): List<NotionBlock> {
        val allBlocks = mutableListOf<NotionBlock>()
        var cursor: String? = null
        do {
            val response = service.listChildren(blockId, cursor, 100)
            allBlocks.addAll(response.results.map { it.toDomain() })
            cursor = if (response.hasMore) response.nextCursor else null
        } while (cursor != null)

        return allBlocks
    }

    override suspend fun getAllChildrenRecursive(blockId: String): List<NotionBlock> {
        val allBlocks = mutableListOf<NotionBlock>()
        val blocks = getAllChildren(blockId)
        allBlocks.addAll(blocks)
        blocks.forEach { block ->
            if (block.hasChildren) {
                try {
                    val children = getAllChildrenRecursive(block.id)
                    allBlocks.addAll(children)
                } catch (e: Exception) {
                    println("Failed to retrieve children for block ${block.id}: ${e.message}")
                }
            }
        }
        return allBlocks
    }

    override suspend fun updateTodoChecked(blockId: String, checked: Boolean): NotionBlock {
        val existingBlock = service.retrieve(blockId)
        val updateRequest = when (existingBlock) {
            is BlockDto.ToDo -> {
                BlockDto.ToDo(
                    id = existingBlock.id,
                    archived = existingBlock.archived,
                    createdTime = existingBlock.createdTime,
                    lastEditedTime = existingBlock.lastEditedTime,
                    hasChildren = existingBlock.hasChildren,
                    todo = existingBlock.todo.copy(checked = checked)
                )
            }
            else -> throw IllegalArgumentException("Block $blockId is not a ToDo block")
        }
        return service.update(blockId, updateRequest).toDomain()
    }
}
