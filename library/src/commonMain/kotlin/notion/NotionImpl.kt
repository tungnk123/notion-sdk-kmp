package notion

import core.data.mapper.toDomain
import core.data.model.internal.obj.BlockObject
import core.data.model.internal.request.QueryDatabaseRequest
import core.data.model.internal.response.PageObject
import core.data.model.internal.response.ResultsResponse
import core.data.model.internal.response.RetrieveDatabaseResponse
import core.data.model.result.NotionBlock
import core.data.model.NotionApiVersion
import core.data.model.result.NotionDatabaseRow
import core.data.model.result.NotionDatabaseSchema
import core.data.model.result.NotionResults
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.TextContent
import io.ktor.serialization.kotlinx.json.*
import kotlinx.atomicfu.atomic
import kotlinx.serialization.json.Json

internal class NotionImpl(
    token: String,
    private val version: NotionApiVersion,
    private var httpClient: HttpClient,
) : Notion {

    private val tokenRef = atomic(token)

    init { httpClient = httpClient.withTokenAndVersion() }

    override val token: String
        get() = tokenRef.value

    override fun setToken(token: String) {
        tokenRef.value = token
    }

    override fun setHttpClient(newHttpClient: HttpClient) {
        httpClient = newHttpClient.withTokenAndVersion()
    }

    override fun close() = httpClient.close()

    private fun HttpClient.withTokenAndVersion(): HttpClient =
        config {
            install(ContentNegotiation) {
                json(
                    Json {
                        encodeDefaults = false
                        ignoreUnknownKeys = true
                        classDiscriminator = "type"
                        explicitNulls = false
                    }
                )
            }
            defaultRequest {
                header(HttpHeaders.Authorization, "Bearer ${tokenRef.value}")
                header(Notion.HEADER_VERSION, version.stringValue)
                contentType(ContentType.Application.Json)
            }
        }

    override suspend fun queryDatabase(
        databaseId: String,
        startCursor: String?,
        pageSize: Int?,
    ): NotionResults<NotionDatabaseRow> {
        val resp: ResultsResponse<PageObject> =
            httpClient.post("${Notion.API_BASE_URL}/$ENDPOINT_DATABASES/$databaseId/$PATH_QUERY") {
                setBody(
                    QueryDatabaseRequest(
                        startCursor = startCursor,
                        pageSize = pageSize,
                    )
                )
            }.body()
        return resp.toDomain()
    }

    override suspend fun queryDatabase(
        databaseId: String,
        jsonRequestBody: String,
    ): NotionResults<NotionDatabaseRow> {
        val resp: ResultsResponse<PageObject> =
            httpClient.post("${Notion.API_BASE_URL}/$ENDPOINT_DATABASES/$databaseId/$PATH_QUERY") {
                setBody(TextContent(jsonRequestBody, ContentType.Application.Json))
            }.body()
        return resp.toDomain()
    }

    override suspend fun retrieveDatabase(databaseId: String): NotionDatabaseSchema {
        val resp: RetrieveDatabaseResponse =
            httpClient.get("${Notion.API_BASE_URL}/$ENDPOINT_DATABASES/$databaseId").body()
        return resp.toDomain()
    }

    override suspend fun retrieveBlock(blockId: String): NotionBlock {
        val resp: BlockObject =
            httpClient.get("${Notion.API_BASE_URL}/$ENDPOINT_BLOCKS/$blockId").body()
        return resp.toDomain()
    }

    override suspend fun retrieveBlockChildren(
        blockId: String,
        startCursor: String?,
        pageSize: Int?,
    ): NotionResults<NotionBlock> {
        val resp: ResultsResponse<BlockObject> =
            httpClient.get("${Notion.API_BASE_URL}/$ENDPOINT_BLOCKS/$blockId/$PATH_CHILDREN") {
                if (startCursor != null) parameter(QUERY_PARAM_START_CURSOR, startCursor)
                if (pageSize != null) parameter(QUERY_PARAM_PAGE_SIZE, pageSize)
            }.body()
        return resp.toDomain()
    }

    companion object {
        private const val ENDPOINT_BLOCKS = "blocks"
        private const val ENDPOINT_DATABASES = "databases"
        private const val PATH_QUERY = "query"
        private const val PATH_CHILDREN = "children"
        private const val QUERY_PARAM_START_CURSOR = "start_cursor"
        private const val QUERY_PARAM_PAGE_SIZE = "page_size"
    }
}