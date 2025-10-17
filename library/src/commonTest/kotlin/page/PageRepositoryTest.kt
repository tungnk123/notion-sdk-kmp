package page

import io.github.tungnk123.notionsdkkmp.auth.TokenProvider
import core.data.model.internal.dto.block.ParentDto
import core.data.model.internal.dto.page.PagePropertyDto
import core.data.model.internal.request.page.CreatePageRequest
import core.data.model.internal.request.page.UpdatePageRequest
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
import io.github.tungnk123.notionsdkkmp.repository.page.PageRepository
import io.github.tungnk123.notionsdkkmp.repository.page.PageRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.page.PageService
import io.github.tungnk123.notionsdkkmp.service.page.PageServiceImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

private class StaticTokenProvider(private val t: String = "x") : TokenProvider {
    override fun token(): String = t
}

private fun notionHttpWith(handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData): NotionHttp {
    val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }
    val client = HttpClient(MockEngine) {
        install(ContentNegotiation) { json(json) }
        engine { addHandler(handler) }
    }
    return NotionHttp(StaticTokenProvider(), client)
}

class PageRepositoryTest {
    @Test
    fun retrieve_ok() = runTest {
        val pageId = "c4d39556-6364-46a1-8a61-ebbb668f7445"
        val response = """
        {
          "object": "page",
          "id": "$pageId",
          "created_time": "2021-04-27T20:38:00.000Z",
          "last_edited_time": "2022-03-02T05:22:00.000Z",
          "created_by": {"object":"user","id":"6794760a-1f15-45cd-9c65-0dfe42f5135a"},
          "last_edited_by": {"object":"user","id":"92a680bb-6970-4726-952b-4f4c03bff617"},
          "cover": null,
          "icon": {"type":"emoji","emoji":"📕"},
          "parent": {"type":"page_id","page_id":"c1218692-102d-4b47-ab38-c21900b3557b"},
          "archived": false,
          "properties": {
            "title": {
              "id":"title",
              "type":"title",
              "title":[
                {
                  "type":"text",
                  "text":{"content":"Reading List","link":null},
                  "annotations":{"bold":false,"italic":false,"strikethrough":false,"underline":false,"code":false,"color":"default"},
                  "plain_text":"Reading List",
                  "href":null
                }
              ]
            }
          },
          "url":"https://www.notion.so/Reading-List-c4d39556636446a18a61ebbb668f7445"
        }
        """.trimIndent()

        val http = notionHttpWith { req ->
            assertEquals(HttpMethod.Get, req.method)
            assertTrue(req.url.fullPath.contains("/pages/$pageId"))
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val service: PageService = PageServiceImpl(http)
        val repo: PageRepository = PageRepositoryImpl(service)

        val page: NotionPage = repo.retrieve(pageId)
        assertEquals(pageId, page.id)
        assertEquals("https://www.notion.so/Reading-List-c4d39556636446a18a61ebbb668f7445", page.url)
        assertTrue(page.properties.containsKey("title"))
        println("✅ retrieve_ok title=${page.properties["title"]}")
    }

    @Test
    fun create_ok() = runTest {
        val req = CreatePageRequest(
            parent = ParentDto.PageId("some-page-id"),
            properties = mapOf()
        )

        val response = """
        {
          "object": "page",
          "id": "59833787-2cf9-4fdf-8782-e53db20768a5",
          "created_time": "2022-03-01T19:05:00.000Z",
          "last_edited_time": "2022-07-06T19:16:00.000Z",
          "created_by": {"object":"user","id":"ee5f0f84-409a-440f-983a-a5315961c6e4"},
          "last_edited_by": {"object":"user","id":"ee5f0f84-409a-440f-983a-a5315961c6e4"},
          "cover": {"type":"external","external":{"url":"https://upload.wikimedia.org/wikipedia/commons/6/62/Tuscankale.jpg"}},
          "icon": {"type":"emoji","emoji":"🥬"},
          "parent": {
            "type": "data_source_id",
            "data_source_id": "d9824bdc-8445-4327-be8b-5b47500af6ce",
            "database_id": "9ce034a5-74ca-4259-8b01-8494453204fe"
          },
          "archived": false,
          "properties": {
            "Price": {
                "id": "BJXS",
                "type": "number",
                "number": 2.5
                }
          },
          "url": "https://www.notion.so/Tuscan-Kale-598337872cf94fdf8782e53db20768a5"
        }
    """.trimIndent()

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Post, r.method)
            assertTrue(r.url.fullPath.endsWith("/pages"))

            val sent = r.body.toByteArray().decodeToString()
            val bodyJson = Json.parseToJsonElement(sent).jsonObject
            val parent = bodyJson["parent"]?.jsonObject
            assertNotNull(parent)
            assertTrue(
                parent.containsKey("database_id") || parent.containsKey("page_id") || parent.containsKey("data_source_id")
            )
            assertNotNull(bodyJson["properties"])

            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val repo: PageRepository = PageRepositoryImpl(PageServiceImpl(http))
        val created = repo.create(req)
        assertEquals("59833787-2cf9-4fdf-8782-e53db20768a5", created.id)
        assertEquals("https://www.notion.so/Tuscan-Kale-598337872cf94fdf8782e53db20768a5", created.url)
        println("✅ create_ok id=${created.id}")
    }

    @Test
    fun update_ok() = runTest {
        val pageId = "59833787-2cf9-4fdf-8782-e53db20768a5"
        val req = UpdatePageRequest(
            properties = mapOf(
                "Score /5" to PagePropertyDto.Select(
                    id = ")Y7%22",
                    select = PagePropertyDto.Select.Value(
                        id = "b7307e35-c80a-4cb5-bb6b-6054523b394a",
                        name = "⭐️⭐️⭐️⭐️",
                        color = "default"
                    )
                ),
                "Read" to PagePropertyDto.Checkbox(
                    id = "_MWJ",
                    checkbox = true
                ),
                "Status" to PagePropertyDto.Select(
                    id = "%60zz5",
                    select = PagePropertyDto.Select.Value(
                        id = "5925ba22-0126-4b58-90c7-b8bbb2c3c895",
                        name = "Reading",
                        color = "red"
                    )
                ),
                "Author" to PagePropertyDto.MultiSelect(
                    id = "qNw_",
                    multiSelect = listOf(
                        PagePropertyDto.Select.Value(
                            id = "833e2c78-35ed-4601-badc-50c323341d76",
                            name = "Kara Swisher",
                            color = "default"
                        )
                    )
                ),
                "Name" to PagePropertyDto.Title(
                    id = "title",
                    title = listOf(
                        PagePropertyDto.Title.Value(
                            type = "text",
                            text = PagePropertyDto.Title.Value.Text(
                                content = "Who Will Teach Silicon Valley to Be Ethical? "
                            ),
                            annotations = PagePropertyDto.Title.Value.Annotations(
                                bold = false, italic = false, strikethrough = false,
                                underline = false, code = false, color = "default"
                            ),
                            plainText = "Who Will Teach Silicon Valley to Be Ethical? ",
                            href = null
                        )
                    )
                )
            ),
            archived = false
        )

        val response = """
        {
          "object": "page",
          "id": "$pageId",
          "created_time": "2022-03-01T19:05:00.000Z",
          "last_edited_time": "2022-07-06T20:25:00.000Z",
          "created_by": {"object":"user","id":"ee5f0f84-409a-440f-983a-a5315961c6e4"},
          "last_edited_by": {"object":"user","id":"0c3e9826-b8f7-4f73-927d-2caaf86f1103"},
          "cover": {"type":"external","external":{"url":"https://upload.wikimedia.org/wikipedia/commons/6/62/Tuscankale.jpg"}},
          "icon": {"type":"emoji","emoji":"🐞"},
          "parent": {
            "type": "data_source_id",
            "data_source_id": "d9824bdc-8445-4327-be8b-5b47500af6ce",
            "database_id": "9ce034a5-74ca-4259-8b01-8494453204fe"
          },
          "archived": false,
          "properties": {
            "Food group": {
              "id": "A%40Hk",
              "type": "select",
              "select": { "id":"5e8e7e8f-432e-4d8a-8166-1821e10225fc","name":"🥬 Vegetable","color":"pink" }
            },
            "Price": { "id": "BJXS", "type":"number", "number": 3.0 },
            "Name": {
              "id": "title",
              "type": "title",
              "title": [
                {
                  "type":"text",
                  "text":{"content":"Tuscan kale (updated)","link":null},
                  "annotations":{"bold":false,"italic":false,"strikethrough":false,"underline":false,"code":false,"color":"default"},
                  "plain_text":"Tuscan kale (updated)",
                  "href":null
                }
              ]
            }
          },
          "url": "https://www.notion.so/Tuscan-kale-598337872cf94fdf8782e53db20768a5",
          "public_url": null
        }
    """.trimIndent()

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Patch, r.method)
            assertTrue(r.url.fullPath.contains("/pages/$pageId"))

            val sent = r.body.toByteArray().decodeToString()
            val bodyJson = Json.parseToJsonElement(sent).jsonObject

            val props = bodyJson["properties"]?.jsonObject
            assertNotNull(props)
            listOf("Score /5", "Read", "Status", "Author", "Name").forEach { key ->
                assertTrue(props.containsKey(key), "properties must contain '$key'")
            }

            bodyJson["archived"]?.let { archivedJson ->
                assertTrue(archivedJson.toString() == "true" || archivedJson.toString() == "false")
            }

            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val repo = PageRepositoryImpl(PageServiceImpl(http))
        val updated = repo.update(pageId, req)
        assertEquals(pageId, updated.id)
        val title = (updated.properties["Name"] ?: updated.properties["title"])
        assertNotNull(title, "title/Name property must exist")
        println("✅ update_ok id=${updated.id}")
    }

    @Test
    fun retrieve_property_item_ok() = runTest {
        val pageId = "59833787-2cf9-4fdf-8782-e53db20768a5"
        val propertyId = "title"

        val response = """
        {
          "object":"list",
          "type":"property_item",
          "results":[
            {
              "object":"property_item",
              "id":"title",
              "type":"title",
              "title":{}
            }
          ],
          "next_url": null
        }
        """.trimIndent()

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Get, r.method)
            assertTrue(r.url.fullPath.contains("/pages/$pageId/properties/$propertyId"))
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val repo: PageRepository = PageRepositoryImpl(PageServiceImpl(http))
        val item = repo.retrievePropertyItem(pageId, propertyId, null, null)
        assertEquals("list", item["object"]?.toString()?.trim('"'))
        assertEquals("property_item", item["type"]?.toString()?.trim('"'))
        println("✅ retrieve_property_item_ok")
    }
}
