package service.block

import core.data.model.internal.dto.block.BlockDto
import core.data.model.internal.request.block.AppendBlockChildrenRequest
import core.data.model.internal.response.ResultsResponseDto
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.request.*

private object Routes {
    private const val BLOCKS = "blocks"
    fun retrieve(id: String) = "$BLOCKS/$id"
    fun update(id: String) = "$BLOCKS/$id"
    fun delete(id: String) = "$BLOCKS/$id"
    fun children(id: String) = "$BLOCKS/$id/children"
}

class BlockServiceImpl(
    private val http: NotionHttp
) : BlockService {

    override suspend fun retrieve(blockId: String): BlockDto = http.get(Routes.retrieve(blockId))

    override suspend fun update(blockId: String, request: BlockDto): BlockDto =
        http.patch(Routes.update(blockId)) { setBody(request) }

    override suspend fun delete(blockId: String): BlockDto = http.delete(Routes.delete(blockId))

    override suspend fun listChildren(
        blockId: String, startCursor: String?, pageSize: Int?
    ): ResultsResponseDto<BlockDto> = http.get(Routes.children(blockId)) {
        if (startCursor != null) url.parameters.append("start_cursor", startCursor)
        if (pageSize != null) url.parameters.append("page_size", pageSize.toString())
    }

    override suspend fun appendChildren(
        blockId: String, request: AppendBlockChildrenRequest
    ): ResultsResponseDto<BlockDto> = http.post(Routes.children(blockId)) {
        setBody(request)
    }
}
