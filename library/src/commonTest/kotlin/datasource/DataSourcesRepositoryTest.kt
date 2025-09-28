package datasource

import auth.TokenProvider
import core.data.model.internal.dto.datasource.DataSourcePropertyDto
import core.data.model.internal.dto.datasource.DataSourcePropertyType
import core.data.model.internal.dto.datasource.EmptyObj
import core.data.model.internal.dto.datasource.ParentDto
import core.data.model.internal.request.datasource.CreateDataSourceRequest
import core.data.model.internal.request.datasource.QueryDataSourceRequest
import http.NotionHttp
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import repository.datasource.DataSourcesRepository
import repository.datasource.DataSourcesRepositoryImpl
import service.datasource.DataSourcesService
import service.datasource.DataSourcesServiceImpl
import kotlin.test.*

private class StaticTokenProvider(private val t: String = "x") : TokenProvider {
    override fun token(): String = t
}

private fun notionHttpWith(handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData): NotionHttp {
    val json = Json { ignoreUnknownKeys = true; classDiscriminator = "type"; explicitNulls = false }
    val client = HttpClient(MockEngine) {
        install(ContentNegotiation) { json(json) }
        engine { addHandler(handler) }
    }
    return NotionHttp(StaticTokenProvider(), client)
}

class DataSourcesRepositoryTest {

    @Test
    fun retrieve_ok() = runTest {
        val dsId = "abc123"
        val body = """
            {
              "object": "data_source",
              "id": "$dsId",
              "properties": {},
              "parent": { "type":"database_id","database_id":"db_1" },
              "created_time": "2021-07-08T23:50:00.000Z",
              "last_edited_time": "2021-07-08T23:50:00.000Z",
              "archived": false,
              "in_trash": false
            }
        """.trimIndent()

        val http = notionHttpWith { req ->
            assertEquals(HttpMethod.Get, req.method)
            assertTrue(req.url.fullPath.contains("/data_sources/$dsId"))
            respond(
                content = body,
                headers = headersOf(HttpHeaders.ContentType, "application/json"),
                status = HttpStatusCode.OK
            )
        }

        val service: DataSourcesService = DataSourcesServiceImpl(http)
        val repo: DataSourcesRepository = DataSourcesRepositoryImpl(service)

        val ds = repo.retrieve(dsId)
        assertEquals(dsId, ds.id)
    }

    @Test
    fun create_ok() = runTest {
        val req = CreateDataSourceRequest(
            parent = ParentDto.DatabaseId("db_1"),
            properties = mapOf(
                "Title" to DataSourcePropertyDto(
                    id = "title",
                    name = "Title",
                    type = DataSourcePropertyType.TITLE,
                    title = EmptyObj
                )
            )
        )

        val response = """
            { "object":"data_source","id":"new_ds","properties":{} }
        """.trimIndent()

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Post, r.method)
            assertTrue(r.url.fullPath.contains("/data_sources"))
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    listOf(ContentType.Application.Json.toString())
                )
            )
        }

        val repo = DataSourcesRepositoryImpl(DataSourcesServiceImpl(http))
        val created = repo.create(req)
        assertEquals("new_ds", created.id)
    }

    @Test
    fun query_empty_results_ok() = runTest {
        val dsId = "abc123"
        val response = """
            { "object":"list", "results": [], "has_more": false, "next_cursor": null }
        """.trimIndent()

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Post, r.method)
            assertTrue(r.url.fullPath.contains("/data_sources/$dsId/query"))
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    listOf(ContentType.Application.Json.toString())
                )
            )
        }

        val repo = DataSourcesRepositoryImpl(DataSourcesServiceImpl(http))
        val res = repo.query(dsId, QueryDataSourceRequest(pageSize = 1))
        assertFalse(res.hasMore)
        assertTrue(res.results.isEmpty())
        assertNull(res.nextCursor)
    }
}
