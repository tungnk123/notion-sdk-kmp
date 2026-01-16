package database

import io.github.tungnk123.notionsdkkmp.auth.TokenProvider
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.block.ParentDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyType
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.CreateDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.InitialDataSourceRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.QueryDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.UpdateDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.datasource.Sort
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonObject
import io.github.tungnk123.notionsdkkmp.repository.database.DatabaseRepository
import io.github.tungnk123.notionsdkkmp.repository.database.DatabaseRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.database.DatabaseService
import io.github.tungnk123.notionsdkkmp.service.database.DatabaseServiceImpl
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

class DatabasesRepositoryTest {

    @Test
    fun retrieve_ok() = runTest {
        val dbId = "248104cd-477e-80fd-b757-e945d38000bd"
        val body = """
            {
              "object": "database",
              "id": "$dbId",
              "title": [
                {
                  "type": "text",
                  "text": { "content": "My Task Tracker", "link": null },
                  "annotations": {
                    "bold": false, "italic": false, "strikethrough": false,
                    "underline": false, "code": false, "color": "default"
                  },
                  "plain_text": "My Task Tracker",
                  "href": null
                }
              ],
              "parent": { "type": "page_id", "page_id": "255104cd-477e-808c-b279-d39ab803a7d2" },
              "is_inline": false,
              "in_trash": false,
              "created_time": "2025-08-07T10:11:07.504-07:00",
              "last_edited_time": "2025-08-10T15:53:11.386-07:00",
              "data_sources": [
                { "id": "248104cd-477e-80af-bc30-000bd28de8f9", "name": "My Task Tracker" }
              ],
              "icon": null,
              "cover": null
            }
        """.trimIndent()

        val http = notionHttpWith { req ->
            assertEquals(HttpMethod.Get, req.method)
            assertTrue(req.url.fullPath.contains("/databases/$dbId"))
            respond(
                content = body,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()),
                status = HttpStatusCode.OK
            )
        }

        val service: DatabaseService = DatabaseServiceImpl(http)
        val repo: DatabaseRepository = DatabaseRepositoryImpl(service)

        val db = repo.retrieve(dbId)
        assertEquals(dbId, db.id)
        assertFalse(db.inTrash)
        assertFalse(db.isInline)
        assertTrue(db.title.any { it.plainText == "My Task Tracker" })
        assertEquals(1, db.dataSources.size)
        assertEquals("My Task Tracker", db.dataSources.first().name)
    }

    @Test
    fun create_with_initial_ds_rollup_ok() = runTest {
        val req =
            CreateDatabaseRequest(
                parent = ParentDto.PageId(
                    "255104cd-477e-808c-b279-d39ab803a7d2"
                ),
                initialDataSource = InitialDataSourceRequest(
                    properties = mapOf(
                        "Count" to DataSourcePropertyDto(
                            id = "rollup",
                            name = "Count",
                            type = DataSourcePropertyType.ROLLUP,
                            rollup = _root_ide_package_.io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.RollupConfig(
                                function = "count"
                            )
                        )
                    )
                )
            )

        val response = """
            {
              "object": "database",
              "id": "248104cd-477e-80fd-b757-e945d38000bd",
              "title": [
                {
                  "type": "text",
                  "text": { "content": "My Task Tracker", "link": null },
                  "annotations": {
                    "bold": false, "italic": false, "strikethrough": false,
                    "underline": false, "code": false, "color": "default"
                  },
                  "plain_text": "My Task Tracker",
                  "href": null
                }
              ],
              "parent": { "type": "page_id", "page_id": "255104cd-477e-808c-b279-d39ab803a7d2" },
              "is_inline": false,
              "in_trash": false,
              "created_time": "2025-08-07T10:11:07.504-07:00",
              "last_edited_time": "2025-08-10T15:53:11.386-07:00",
              "data_sources": [
                { "id": "248104cd-477e-80af-bc30-000bd28de8f9", "name": "My Task Tracker" }
              ],
              "icon": null,
              "cover": null
            }
        """.trimIndent()

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Post, r.method)
            assertTrue(r.url.fullPath.contains("/databases"))
            val sent = r.body.toByteArray().decodeToString()
            assertTrue(sent.contains("initial_data_source"))
            assertTrue(sent.contains("\"rollup\""))
            assertTrue(sent.contains("\"count\""))
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val repo = DatabaseRepositoryImpl(DatabaseServiceImpl(http))
        val created = repo.create(req)
        assertEquals("248104cd-477e-80fd-b757-e945d38000bd", created.id)
        assertTrue(created.title.any { it.plainText == "My Task Tracker" })
        assertEquals(
            "255104cd-477e-808c-b279-d39ab803a7d2",
            (created.parent as? io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionParent.PageId)?.pageId
        )
    }

    @Test
    fun update_parent_ok() = runTest {
        val dbId = "248104cd-477e-80fd-b757-e945d38000bd"
        val req =
            UpdateDatabaseRequest(
                parent = ParentDto.PageId(
                    "255104cd-477e-808c-b279-d39ab803a7d2"
                )
            )

        val response = """
            {
              "object": "database",
              "id": "$dbId",
              "title": [
                {
                  "type": "text",
                  "text": { "content": "My Task Tracker", "link": null },
                  "annotations": {
                    "bold": false, "italic": false, "strikethrough": false,
                    "underline": false, "code": false, "color": "default"
                  },
                  "plain_text": "My Task Tracker",
                  "href": null
                }
              ],
              "parent": { "type": "page_id", "page_id": "255104cd-477e-808c-b279-d39ab803a7d2" },
              "is_inline": false,
              "in_trash": false,
              "created_time": "2025-08-07T10:11:07.504-07:00",
              "last_edited_time": "2025-08-10T15:53:11.386-07:00",
              "data_sources": [
                { "id": "248104cd-477e-80af-bc30-000bd28de8f9", "name": "My Task Tracker" }
              ],
              "icon": null,
              "cover": null
            }
        """.trimIndent()

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Patch, r.method)
            assertTrue(r.url.fullPath.contains("/databases/$dbId"))
            val sent = r.body.toByteArray().decodeToString()
            assertTrue(sent.contains("\"parent\""))
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val repo = DatabaseRepositoryImpl(DatabaseServiceImpl(http))
        val updated = repo.update(dbId, req)
        assertEquals(dbId, updated.id)
        assertEquals(
            "255104cd-477e-808c-b279-d39ab803a7d2",
            (updated.parent as? io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionParent.PageId)?.pageId
        )
        assertNotNull(updated.lastEditedTime)
    }

    @Test
    fun query_ok() = runTest {
        val dbId = "248104cd-477e-80fd-b757-e945d38000bd"
        val pageId = "page-123-456"
        val response = """
            {
              "object": "list",
              "results": [
                {
                  "object": "page",
                  "id": "$pageId",
                  "created_time": "2025-08-07T10:11:07.504Z",
                  "last_edited_time": "2025-08-10T15:53:11.386Z",
                  "created_by": { "object": "user", "id": "user-123" },
                  "last_edited_by": { "object": "user", "id": "user-123" },
                  "archived": false,
                  "in_trash": false,
                  "parent": { "type": "database_id", "database_id": "$dbId" },
                  "properties": {
                    "Name": {
                      "id": "title",
                      "type": "title",
                      "title": [
                        {
                          "type": "text",
                          "text": { "content": "Test Page", "link": null },
                          "plain_text": "Test Page"
                        }
                      ]
                    }
                  },
                  "url": "https://www.notion.so/$pageId"
                }
              ],
              "next_cursor": "cursor-abc",
              "has_more": true
            }
        """.trimIndent()

        val req = QueryDatabaseRequest(
            sorts = listOf(Sort(timestamp = "created_time", direction = "descending")),
            pageSize = 10
        )

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Post, r.method)
            assertTrue(r.url.fullPath.contains("/databases/$dbId/query"))
            val sent = r.body.toByteArray().decodeToString()
            assertTrue(sent.contains("\"sorts\""))
            assertTrue(sent.contains("\"created_time\""))
            assertTrue(sent.contains("\"descending\""))
            assertTrue(sent.contains("\"page_size\""))
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val repo = DatabaseRepositoryImpl(DatabaseServiceImpl(http))
        val results = repo.query(dbId, req)

        assertEquals(1, results.results.size)
        assertEquals(pageId, results.results.first().id)
        assertEquals("cursor-abc", results.nextCursor)
        assertTrue(results.hasMore)
    }

    @Test
    fun query_with_filter_ok() = runTest {
        val dbId = "248104cd-477e-80fd-b757-e945d38000bd"
        val response = """
            {
              "object": "list",
              "results": [],
              "next_cursor": null,
              "has_more": false
            }
        """.trimIndent()

        val filterJson = buildJsonObject {
            put("property", JsonPrimitive("Status"))
            putJsonObject("select") {
                put("equals", JsonPrimitive("Done"))
            }
        }

        val req = QueryDatabaseRequest(
            filter = filterJson,
            startCursor = "prev-cursor",
            pageSize = 50
        )

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Post, r.method)
            assertTrue(r.url.fullPath.contains("/databases/$dbId/query"))
            val sent = r.body.toByteArray().decodeToString()
            assertTrue(sent.contains("\"filter\""))
            assertTrue(sent.contains("\"Status\""))
            assertTrue(sent.contains("\"select\""))
            assertTrue(sent.contains("\"Done\""))
            assertTrue(sent.contains("\"start_cursor\""))
            assertTrue(sent.contains("\"prev-cursor\""))
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val repo = DatabaseRepositoryImpl(DatabaseServiceImpl(http))
        val results = repo.query(dbId, req)

        assertEquals(0, results.results.size)
        assertNull(results.nextCursor)
        assertFalse(results.hasMore)
    }

    @Test
    fun query_child_database_ok() = runTest {
        // This tests querying a child_database (inline database) using its block ID
        // When you have a page with blocks, one of them can be type "child_database"
        // You can query that database using the block ID via POST /databases/{block_id}/query
        val childDatabaseId = "2dfc83ce-d81d-802b-b2ff-e10d3fbf0946"
        val pageId1 = "page-item-1"
        val pageId2 = "page-item-2"
        val response = """
            {
              "object": "list",
              "results": [
                {
                  "object": "page",
                  "id": "$pageId1",
                  "created_time": "2026-01-05T14:56:00.000Z",
                  "last_edited_time": "2026-01-16T15:07:00.000Z",
                  "created_by": { "object": "user", "id": "user-123" },
                  "last_edited_by": { "object": "user", "id": "user-123" },
                  "archived": false,
                  "in_trash": false,
                  "parent": { "type": "database_id", "database_id": "$childDatabaseId" },
                  "properties": {
                    "Name": {
                      "id": "title",
                      "type": "title",
                      "title": [
                        {
                          "type": "text",
                          "text": { "content": "Task 1", "link": null },
                          "plain_text": "Task 1"
                        }
                      ]
                    },
                    "Status": {
                      "id": "status",
                      "type": "checkbox",
                      "checkbox": true
                    }
                  },
                  "url": "https://www.notion.so/$pageId1"
                },
                {
                  "object": "page",
                  "id": "$pageId2",
                  "created_time": "2026-01-06T10:00:00.000Z",
                  "last_edited_time": "2026-01-16T12:00:00.000Z",
                  "created_by": { "object": "user", "id": "user-123" },
                  "last_edited_by": { "object": "user", "id": "user-123" },
                  "archived": false,
                  "in_trash": false,
                  "parent": { "type": "database_id", "database_id": "$childDatabaseId" },
                  "properties": {
                    "Name": {
                      "id": "title",
                      "type": "title",
                      "title": [
                        {
                          "type": "text",
                          "text": { "content": "Task 2", "link": null },
                          "plain_text": "Task 2"
                        }
                      ]
                    },
                    "Status": {
                      "id": "status",
                      "type": "checkbox",
                      "checkbox": false
                    }
                  },
                  "url": "https://www.notion.so/$pageId2"
                }
              ],
              "next_cursor": null,
              "has_more": false
            }
        """.trimIndent()

        val req = QueryDatabaseRequest(pageSize = 100)

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Post, r.method)
            // The key assertion: querying child_database uses /databases/{block_id}/query
            assertTrue(r.url.fullPath.contains("/databases/$childDatabaseId/query"))
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val repo = DatabaseRepositoryImpl(DatabaseServiceImpl(http))
        val results = repo.query(childDatabaseId, req)

        assertEquals(2, results.results.size)
        assertEquals(pageId1, results.results[0].id)
        assertEquals(pageId2, results.results[1].id)
        assertNull(results.nextCursor)
        assertFalse(results.hasMore)
    }
}
