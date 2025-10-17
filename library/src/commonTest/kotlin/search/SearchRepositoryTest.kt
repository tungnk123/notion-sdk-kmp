package search

import auth.TokenProvider
import core.data.model.result.common.NotionResults
import core.data.model.result.datasource.NotionDataSource
import core.data.model.result.page.NotionPage
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import repository.search.SearchRepository
import repository.search.SearchRepositoryImpl
import service.search.SearchServiceImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private class StaticTokenProvider(private val t: String = "x") : TokenProvider {
    override fun token(): String = t
}

private fun notionHttpWith(handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData): NotionHttp {
    val json = Json { ignoreUnknownKeys = true; explicitNulls = false }
    val client = HttpClient(MockEngine) {
        install(ContentNegotiation) { json(json) }
        engine { addHandler(handler) }
    }
    return NotionHttp(StaticTokenProvider(), client)
}

class SearchRepositoryTest {

    @Test
    fun search_pages_ok() = runTest {
        val queryText = "External tasks"
        val cursor = "abc_cursor"
        val pageSize = 10

        val response = """
        {
          "object": "list",
          "results": [
            {
              "object": "page",
              "id": "954b67f9-3f87-41db-8874-23b92bbd31ee",
              "created_time": "2022-07-06T19:30:00.000Z",
              "last_edited_time": "2022-07-06T19:30:00.000Z",
              "created_by": { "object":"user","id":"ee5f0f84-409a-440f-983a-a5315961c6e4" },
              "last_edited_by": { "object":"user","id":"ee5f0f84-409a-440f-983a-a5315961c6e4" },
              "cover": null,
              "icon": null,
              "parent": { "type":"page_id","page_id":"c1218692-102d-4b47-ab38-c21900b3557b" },
              "archived": false,
              "properties": {
                "title": {
                  "id":"title",
                  "type":"title",
                  "title":[
                    {
                      "type":"text",
                      "text":{"content":"External tasks","link":null},
                      "annotations":{"bold":false,"italic":false,"strikethrough":false,"underline":false,"code":false,"color":"default"},
                      "plain_text":"External tasks",
                      "href":null
                    }
                  ]
                }
              },
              "url":"https://www.notion.so/External-tasks-954b67f93f8741db887423b92bbd31ee"
            }
          ],
          "next_cursor": null,
          "has_more": false
        }
        """.trimIndent()

        val http = notionHttpWith { requestData ->
            assertEquals(HttpMethod.Post, requestData.method)
            assertTrue(requestData.url.fullPath.endsWith("/search"))

            val sent = requestData.body.toByteArray().decodeToString()
            val body = Json.parseToJsonElement(sent).jsonObject
            assertEquals(queryText, body["query"]!!.jsonPrimitive.content)
            assertEquals(cursor, body["start_cursor"]!!.jsonPrimitive.content)
            assertEquals(pageSize.toString(), body["page_size"]!!.jsonPrimitive.content)

            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val repo: SearchRepository = SearchRepositoryImpl(SearchServiceImpl(http))
        val result: NotionResults<NotionPage> = repo.searchPages(
            query = queryText,
            directionAsc = true,
            startCursor = cursor,
            pageSize = pageSize
        )
        assertEquals(false, result.hasMore)
        assertEquals(1, result.results.size)
        val page = result.results.first()
        assertEquals("954b67f9-3f87-41db-8874-23b92bbd31ee", page.id)
        println("✅ search_pages_ok size=${result.results.size}")
    }

    @Test
    fun search_data_sources_ok() = runTest {
        val queryText = "External tasks"
        val pageSize = 5

        val response = """
        {
          "object": "list",
          "results": [
            {
              "object": "data_source",
              "id": "9ce034a5-74ca-4259-8b01-8494453204fe",
              "created_time": "2022-07-06T19:30:00.000Z",
              "last_edited_time": "2022-07-06T19:30:00.000Z",
              "created_by": { "object":"user","id":"ee5f0f84-409a-440f-983a-a5315961c6e4" },
              "last_edited_by": { "object":"user","id":"ee5f0f84-409a-440f-983a-a5315961c6e4" },
              "cover": null,
              "icon": null,
              "title": [
                {
                  "type":"text",
                  "text":{"content":"External tasks","link":null},
                  "annotations":{"bold":false,"italic":false,"strikethrough":false,"underline":false,"code":false,"color":"default"},
                  "plain_text":"External tasks",
                  "href":null
                }
              ],
              "url":"https://www.notion.so/9ce034a574ca42598b018494453204fe"
            }
          ],
          "next_cursor": "next_cursor_value",
          "has_more": true
        }
        """.trimIndent()

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Post, r.method)
            assertTrue(r.url.fullPath.endsWith("/search"))

            val sent = r.body.toByteArray().decodeToString()
            val body = Json.parseToJsonElement(sent).jsonObject
            assertEquals(queryText, body["query"]!!.jsonPrimitive.content)
            assertEquals(pageSize.toString(), body["page_size"]!!.jsonPrimitive.content)

            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val repo: SearchRepository = SearchRepositoryImpl(SearchServiceImpl(http))
        val result: NotionResults<NotionDataSource> = repo.searchDataSources(
            query = queryText,
            directionAsc = false,
            startCursor = null,
            pageSize = pageSize
        )
        assertEquals(true, result.hasMore)
        assertEquals("next_cursor_value", result.nextCursor)
        assertEquals(1, result.results.size)
        val ds = result.results.first()
        assertEquals("9ce034a5-74ca-4259-8b01-8494453204fe", ds.id)
        println("✅ search_data_sources_ok size=${result.results.size}")
    }
}
