package database

import auth.TokenProvider
import core.data.model.internal.dto.block.ParentDto
import core.data.model.internal.dto.datasource.DataSourcePropertyDto
import core.data.model.internal.dto.datasource.DataSourcePropertyType
import core.data.model.internal.request.database.CreateDatabaseRequest
import core.data.model.internal.request.database.InitialDataSourceRequest
import core.data.model.internal.request.database.UpdateDatabaseRequest
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
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
        val req = CreateDatabaseRequest(
            parent = ParentDto.PageId("255104cd-477e-808c-b279-d39ab803a7d2"),
            initialDataSource = InitialDataSourceRequest(
                properties = mapOf(
                    "Count" to DataSourcePropertyDto(
                        id = "rollup",
                        name = "Count",
                        type = DataSourcePropertyType.ROLLUP,
                        rollup = core.data.model.internal.dto.datasource.RollupConfig(function = "count")
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
            (created.parent as? core.data.model.result.common.NotionParent.PageId)?.pageId
        )
    }

    @Test
    fun update_parent_ok() = runTest {
        val dbId = "248104cd-477e-80fd-b757-e945d38000bd"
        val req = UpdateDatabaseRequest(
            parent = ParentDto.PageId("255104cd-477e-808c-b279-d39ab803a7d2")
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
            (updated.parent as? core.data.model.result.common.NotionParent.PageId)?.pageId
        )
        assertNotNull(updated.lastEditedTime)
    }
}
