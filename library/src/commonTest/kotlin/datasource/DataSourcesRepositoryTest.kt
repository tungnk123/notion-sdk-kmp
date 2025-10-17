package datasource

import io.github.tungnk123.notionsdkkmp.auth.TokenProvider
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.block.ParentDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.datasource.CreateDataSourceRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.datasource.QueryDataSourceRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.datasource.UpdateDataSourceRequest
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.github.tungnk123.notionsdkkmp.repository.datasource.DataSourceRepository
import io.github.tungnk123.notionsdkkmp.repository.datasource.DataSourceRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.datasource.DataSourceService
import io.github.tungnk123.notionsdkkmp.service.datasource.DataSourceServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
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

        val service: DataSourceService = DataSourceServiceImpl(http)
        val repo: DataSourceRepository = DataSourceRepositoryImpl(service)

        val ds = repo.retrieve(dsId)
        assertEquals(dsId, ds.id)
    }

    @Test
    fun create_with_rollup_property_ok() = runTest {
        val req =
            CreateDataSourceRequest(
                parent = ParentDto.DatabaseId(
                    "db_1"
                ),
                properties = mapOf(
                    "Rollup" to _root_ide_package_.io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyDto(
                        id = "rollup",
                        name = "Rollup",
                        type = io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyType.ROLLUP,
                        rollup = _root_ide_package_.io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.RollupConfig(
                            function = "count"
                        )
                    )
                )
            )

        val response = """
        {
          "object": "data_source",
          "id": "ds_rollup",
          "properties": {
            "Rollup": {
              "id": "rollup",
              "name": "Rollup",
              "type": "rollup",
              "rollup": { "function": "count" }
            }
          },
          "parent": { "type":"database_id", "database_id":"db_1" },
          "created_time": "2025-08-07T10:11:07.504-07:00",
          "last_edited_time": "2025-08-10T15:53:11.386-07:00",
          "archived": false,
          "in_trash": false
        }
    """.trimIndent()

        val http = notionHttpWith { requestData ->
            assertEquals(HttpMethod.Post, requestData.method)
            assertTrue(requestData.url.fullPath.contains("/data_sources"))

            val sent = requestData.body.toByteArray().decodeToString()
            assertTrue(sent.contains("\"rollup\""), "Body must include rollup object")
            assertTrue(sent.contains("\"function\""), "Body must include function on rollup")
            assertTrue(sent.contains("\"count\""), "Body must set function to count")

            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    listOf(ContentType.Application.Json.toString())
                )
            )
        }

        val repo = DataSourceRepositoryImpl(DataSourceServiceImpl(http))
        val created = repo.create(req)
        assertEquals("ds_rollup", created.id)
    }

    @Test
    fun create_ok() = runTest {
        val req =
            CreateDataSourceRequest(
                parent = ParentDto.DatabaseId(
                    "db_1"
                ),
                properties = mapOf(
                    "Title" to _root_ide_package_.io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyDto(
                        id = "title",
                        name = "Title",
                        type = io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyType.TITLE,
                        title = io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.EmptyObj
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

        val repo = DataSourceRepositoryImpl(DataSourceServiceImpl(http))
        val created = repo.create(req)
        assertEquals("new_ds", created.id)
    }

    @Test
    fun create_full_example_ok() = runTest {
        val req =
            CreateDataSourceRequest(
                parent = ParentDto.DatabaseId(
                    "6ee911d9-189c-4844-93e8-260c1438b6e4"
                ),
                properties = mapOf(
                    "Title" to _root_ide_package_.io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyDto(
                        id = "title",
                        name = "Title",
                        type = io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyType.TITLE,
                        title = io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.EmptyObj
                    ),
                    "Count" to _root_ide_package_.io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyDto(
                        id = "number",
                        name = "Count",
                        type = io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyType.NUMBER,
                        number = _root_ide_package_.io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.NumberConfig()
                    )
                )
            )

        val response =
            """
        {
          "object": "data_source",
          "id": "bc1211ca-e3f1-4939-ae34-5260b16f627c",
          "created_time": "2021-07-08T23:50:00.000Z",
          "last_edited_time": "2021-07-08T23:50:00.000Z",
          "properties": {
            "+1": { "id": "Wp%3DC", "name": "+1", "type": "people", "people": {} },
            "In stock": { "id": "fk%5EY", "name": "In stock", "type": "checkbox", "checkbox": {} },
            "Price": { "id": "evWq", "name": "Price", "type": "number", "number": { "format": "dollar" } },
            "Description": { "id": "V}lX", "name": "Description", "type": "rich_text", "rich_text": {} },
            "Last ordered": { "id": "eVnV", "name": "Last ordered", "type": "date", "date": {} },
            "Meals": {
              "id": "%7DWA~", "name": "Meals", "type": "relation",
              "relation": { "database_id": "668d797c-76fa-4934-9b05-ad288df2d136", "synced_property_name": "Related to Grocery List (Meals)" }
            },
            "Number of meals": {
              "id": "Z\\Eh", "name": "Number of meals", "type": "rollup",
              "rollup": {
                "rollup_property_name": "Name",
                "relation_property_name": "Meals",
                "rollup_property_id": "title",
                "relation_property_id": "mxp^",
                "function": "count"
              }
            },
            "Store availability": {
              "id": "s}Kq", "name": "Store availability", "type": "multi_select",
              "multi_select": { "options": [
                { "id": "cb79b393-d1c1-4528-b517-c450859de766", "name": "Duc Loi Market", "color": "blue" },
                { "id": "58aae162-75d4-403b-a793-3bc7308e4cd2", "name": "Rainbow Grocery", "color": "gray" },
                { "id": "22d0f199-babc-44ff-bd80-a9eae3e3fcbf", "name": "Nijiya Market", "color": "purple" },
                { "id": "0d069987-ffb0-4347-bde2-8e4068003dbc", "name": "Gus's Community Market", "color": "yellow" }
              ] }
            },
            "Photo": { "id": "yfiK", "name": "Photo", "type": "files", "files": {} },
            "Food group": {
              "id": "CM%3EH", "name": "Food group", "type": "select",
              "select": { "options": [
                { "id": "6d4523fa-88cb-4ffd-9364-1e39d0f4e566", "name": "🥦Vegetable", "color": "green" },
                { "id": "268d7e75-de8f-4c4b-8b9d-de0f97021833", "name": "🍎Fruit", "color": "red" },
                { "id": "1b234a00-dc97-489c-b987-829264cfdfef", "name": "💪Protein", "color": "yellow" }
              ] }
            },
            "Name": { "id": "title", "name": "Name", "type": "title", "title": {} }
          },
          "parent": { "type": "database_id", "database_id": "6ee911d9-189c-4844-93e8-260c1438b6e4" },
          "database_parent": { "type": "page_id", "page_id": "98ad959b-2b6a-4774-80ee-00246fb0ea9b" },
          "archived": false,
          "is_inline": false,
          "icon": { "type": "emoji", "emoji": "🎉" },
          "cover": { "type": "external", "external": { "url": "https://website.domain/images/image.png" } },
          "url": "https://www.notion.so/bc1211cae3f14939ae34260b16f627c",
          "title": [
            {
              "type": "text",
              "text": { "content": "Grocery List", "link": null },
              "annotations": {
                "bold": false, "italic": false, "strikethrough": false,
                "underline": false, "code": false, "color": "default"
              },
              "plain_text": "Grocery List",
              "href": null
            }
          ]
        }
        """.trimIndent()

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Post, r.method)
            assertTrue(r.url.fullPath.contains("/data_sources"))
            val sent = r.body.toByteArray().decodeToString()
            assertTrue(sent.contains("\"database_id\""))
            assertTrue(sent.contains("\"type\":\"number\"")) // we send a number property
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, listOf(ContentType.Application.Json.toString()))
            )
        }

        val repo = DataSourceRepositoryImpl(DataSourceServiceImpl(http))
        val created = repo.create(req)
        assertEquals("bc1211ca-e3f1-4939-ae34-5260b16f627c", created.id)
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

        val repo = DataSourceRepositoryImpl(DataSourceServiceImpl(http))
        val res = repo.query(
            dsId,
            QueryDataSourceRequest(
                pageSize = 1
            )
        )
        assertFalse(res.hasMore)
        assertTrue(res.results.isEmpty())
        assertNull(res.nextCursor)
    }

    @Test
    fun update_add_url_property_and_title_ok() = runTest {
        val dsId = "b55c9c91-384d-452b-81db-d1ef79372b75"
        val req =
            UpdateDataSourceRequest(
                properties = mapOf(
                    "Website" to _root_ide_package_.io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyDto(
                        id = "url",
                        name = "Website",
                        type = io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourcePropertyType.URL,
                        url = io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.EmptyObj
                    )
                )
            )

        val response = """
        {
          "object": "data_source",
          "id": "bc1211ca-e3f1-4939-ae34-5260b16f627c",
          "created_time": "2021-07-08T23:50:00.000Z",
          "last_edited_time": "2021-07-08T23:50:00.000Z",
          "properties": {
            "Name":   { "id": "title", "name": "Name", "type": "title", "title": {} },
            "Website": { "id": "url", "name": "Website", "type": "url", "url": {} }
          },
          "parent": { "type": "database_id", "database_id": "6ee911d9-189c-4844-93e8-260c1438b6e4" },
          "database_parent": { "type": "page_id", "page_id": "98ad959b-2b6a-4774-80ee-00246fb0ea9b" },
          "archived": false,
          "is_inline": false,
          "icon": { "type": "emoji", "emoji": "🎉" },
          "cover": { "type": "external", "external": { "url": "https://website.domain/images/image.png" } },
          "url": "https://www.notion.so/bc1211cae3f14939ae34260b16f627c",
          "title": [
            {
              "type": "text",
              "text": { "content": "Grocery List", "link": null },
              "annotations": {
                "bold": false, "italic": false, "strikethrough": false,
                "underline": false, "code": false, "color": "default"
              },
              "plain_text": "Grocery List",
              "href": null
            }
          ]
        }
    """.trimIndent()
        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Patch, r.method)
            assertTrue(r.url.fullPath.contains("/data_sources/$dsId"))

            val sent = r.body.toByteArray().decodeToString()
            assertTrue(sent.contains("\"Website\""))
            assertTrue(sent.contains("\"type\":\"url\""))

            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    listOf(ContentType.Application.Json.toString())
                )
            )
        }

        val repo = DataSourceRepositoryImpl(DataSourceServiceImpl(http))
        val updated = repo.update(dsId, req)
        assertEquals("bc1211ca-e3f1-4939-ae34-5260b16f627c", updated.id)
    }

    @Test
    fun retrieve_full_example_ok() = runTest {
        val dsId = "bc1211ca-e3f1-4939-ae34-5260b16f627c"
        val response = """
        {
          "object": "data_source",
          "id": "bc1211ca-e3f1-4939-ae34-5260b16f627c",
          "created_time": "2021-07-08T23:50:00.000Z",
          "last_edited_time": "2021-07-08T23:50:00.000Z",
          "properties": {
            "Name": { "id": "title", "name": "Name", "type": "title", "title": {} }
          },
          "parent": { "type": "database_id", "database_id": "6ee911d9-189c-4844-93e8-260c1438b6e4" },
          "database_parent": { "type": "page_id", "page_id": "98ad959b-2b6a-4774-80ee-00246fb0ea9b" },
          "archived": false,
          "is_inline": false,
          "icon": { "type": "emoji", "emoji": "🎉" },
          "cover": { "type": "external", "external": { "url": "https://website.domain/images/image.png" } },
          "url": "https://www.notion.so/bc1211cae3f14939ae34260b16f627c",
          "title": [
            {
              "type": "text",
              "text": { "content": "Grocery List", "link": null },
              "annotations": {
                "bold": false,
                "italic": false,
                "strikethrough": false,
                "underline": false,
                "code": false,
                "color": "default"
              },
              "plain_text": "Grocery List",
              "href": null
            }
          ]
        }
    """.trimIndent()

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Get, r.method)
            assertTrue(r.url.fullPath.contains("/data_sources/$dsId"))
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, listOf(ContentType.Application.Json.toString()))
            )
        }

        val repo = DataSourceRepositoryImpl(DataSourceServiceImpl(http))
        val dataSource = repo.retrieve(dsId)

        assertEquals(dsId, dataSource.id)
        assertFalse(dataSource.archived, "Data source should not be archived")
        assertTrue(dataSource.properties.containsKey("Name"))
        assertEquals("Grocery List", dataSource.title.first().plainText)
        println("DataSource ID: ${dataSource.id}")
        println("Parent: ${dataSource.parent}")
        println("Title: ${dataSource.title.joinToString { it.plainText }}")
        println("Icon: ${dataSource.icon}")
        println("Cover: ${dataSource.cover}")
        println("Properties: ${dataSource.properties.keys}")
    }

}
