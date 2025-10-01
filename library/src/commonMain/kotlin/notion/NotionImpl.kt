package notion

import auth.TokenProvider
import core.data.mapper.toDomain
import core.data.model.NotionApiVersion
import core.data.model.internal.dto.BlockDto
import core.data.model.internal.dto.page.PageDto
import core.data.model.internal.request.database.QueryDatabaseRequest
import core.data.model.internal.response.ResultsResponseDto
import core.data.model.internal.response.RetrieveDatabaseResponseDto
import core.data.model.result.NotionBlock
import core.data.model.result.NotionDatabaseSchema
import core.data.model.result.NotionResults
import core.data.model.result.page.NotionPage
import http.NotionHttp
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.atomicfu.atomic
import kotlin.concurrent.Volatile

internal class NotionImpl(
    token: String,
    private val version: NotionApiVersion,
    client: HttpClient,
) : Notion {

    private val tokenRef = atomic(token)

    private class RefTokenProvider(
        private val ref: kotlinx.atomicfu.AtomicRef<String>
    ) : TokenProvider {
        override fun token(): String = ref.value
    }

    @Volatile
    private var http = NotionHttp(
        tokenProvider = RefTokenProvider(tokenRef), client = client, apiVersion = version
    )

    override val token: String get() = tokenRef.value

    override fun setToken(token: String) {
        tokenRef.value = token
    }

    override fun setHttpClient(newHttpClient: HttpClient) {
        http = NotionHttp(
            tokenProvider = RefTokenProvider(tokenRef),
            client = newHttpClient,
            apiVersion = version
        )
    }

    override fun close() {}

    override suspend fun queryDatabase(
        databaseId: String,
        startCursor: String?,
        pageSize: Int?,
    ): NotionResults<NotionPage> {
        val resp: ResultsResponseDto<PageDto> = http.post(Routes.queryDatabase(databaseId)) {
            setBody(QueryDatabaseRequest(startCursor = startCursor, pageSize = pageSize))
        }
        return resp.toDomain(PageDto::toDomain)
    }

    override suspend fun queryDatabase(
        databaseId: String,
        jsonRequestBody: String,
    ): NotionResults<NotionPage> {
        val resp: ResultsResponseDto<PageDto> = http.post(Routes.queryDatabase(databaseId)) {
            setBody(TextContent(jsonRequestBody, ContentType.Application.Json))
        }
        return resp.toDomain(PageDto::toDomain)
    }

    override suspend fun retrieveDatabase(databaseId: String): NotionDatabaseSchema {
        val resp: RetrieveDatabaseResponseDto = http.get(Routes.retrieveDatabase(databaseId))
        return resp.toDomain()
    }

    override suspend fun retrieveBlock(blockId: String): NotionBlock {
        val resp: BlockDto = http.get(Routes.retrieveBlock(blockId))
        return resp.toDomain()
    }

    override suspend fun retrieveBlockChildren(
        blockId: String,
        startCursor: String?,
        pageSize: Int?,
    ): NotionResults<NotionBlock> {
        val resp: ResultsResponseDto<BlockDto> = http.get(Routes.blockChildren(blockId)) {
            if (startCursor != null) parameter(QueryParam.START_CURSOR, startCursor)
            if (pageSize != null) parameter(QueryParam.PAGE_SIZE, pageSize)
        }
        return resp.toDomain(BlockDto::toDomain)
    }

    private object Routes {
        private const val BLOCKS = "blocks"
        private const val DATABASES = "databases"
        private const val QUERY = "query"
        private const val CHILDREN = "children"

        fun queryDatabase(databaseId: String) = "$DATABASES/$databaseId/$QUERY"
        fun retrieveDatabase(databaseId: String) = "$DATABASES/$databaseId"
        fun retrieveBlock(blockId: String) = "$BLOCKS/$blockId"
        fun blockChildren(blockId: String) = "$BLOCKS/$blockId/$CHILDREN"
    }

    private object QueryParam {
        const val START_CURSOR = "start_cursor"
        const val PAGE_SIZE = "page_size"
    }
}