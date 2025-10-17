package block

import io.github.tungnk123.notionsdkkmp.auth.TokenProvider
import core.data.model.internal.dto.block.BlockDto
import core.data.model.internal.dto.richtext.RichTextAnnotations
import core.data.model.internal.dto.richtext.RichTextColor
import core.data.model.internal.dto.richtext.RichTextDto
import core.data.model.internal.response.ResultsResponseDto
import core.data.model.result.block.NotionBlock
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import io.github.tungnk123.notionsdkkmp.repository.block.BlockRepository
import io.github.tungnk123.notionsdkkmp.repository.block.BlockRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.block.BlockService
import io.github.tungnk123.notionsdkkmp.service.block.BlockServiceImpl
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
        classDiscriminator = "type"
    }
    val client = HttpClient(MockEngine) {
        install(ContentNegotiation) { json(json) }
        engine { addHandler(handler) }
    }
    return NotionHttp(StaticTokenProvider(), client)
}

class BlockRepositoryTest {

    @Test
    fun retrieve_ok() = runTest {
        val blockId = "c02fc1d3-db8b-45c5-a222-27595b15aea7"
        val response = """
        {
          "object": "block",
          "id": "$blockId",
          "parent": { "type": "page_id", "page_id": "59833787-2cf9-4fdf-8782-e53db20768a5" },
          "created_time": "2022-03-01T19:05:00.000Z",
          "last_edited_time": "2022-07-06T19:41:00.000Z",
          "created_by": { "object": "user", "id": "ee5f0f84-409a-440f-983a-a5315961c6e4" },
          "last_edited_by": { "object": "user", "id": "ee5f0f84-409a-440f-983a-a5315961c6e4" },
          "has_children": false,
          "archived": false,
          "type": "heading_2",
          "heading_2": {
            "rich_text": [
              {
                "type": "text",
                "text": { "content": "Lacinato kale", "link": null },
                "annotations": { "bold": false, "italic": false, "strikethrough": false, "underline": false, "code": false, "color": "default" },
                "plain_text": "Lacinato kale",
                "href": null
              }
            ],
            "color": "default",
            "is_toggleable": false
          }
        }
        """.trimIndent()

        val http = notionHttpWith { req ->
            assertEquals(HttpMethod.Get, req.method)
            assertTrue(req.url.fullPath.contains("/blocks/$blockId"))
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val repo: BlockRepository = BlockRepositoryImpl(BlockServiceImpl(http))
        val block: NotionBlock = repo.retrieve(blockId)
        assertTrue(block is NotionBlock.HeadingTwo)
        assertEquals(blockId, block.id)
        assertEquals(false, block.hasChildren)
        println("✅ retrieve_ok id=${block.id}")
    }

    @Test
    fun list_children_ok() = runTest {
        val parentId = "b55c9c91-384d-452b-81db-d1ef79372b75"
        val response = """
        {
          "object": "list",
          "results": [
            {
              "object": "block",
              "id": "c02fc1d3-db8b-45c5-a222-27595b15aea7",
              "parent": { "type": "page_id", "page_id": "59833787-2cf9-4fdf-8782-e53db20768a5" },
              "created_time": "2022-03-01T19:05:00.000Z",
              "last_edited_time": "2022-07-06T19:41:00.000Z",
              "created_by": { "object": "user", "id": "ee5f0f84-409a-440f-983a-a5315961c6e4" },
              "last_edited_by": { "object": "user", "id": "ee5f0f84-409a-440f-983a-a5315961c6e4" },
              "has_children": false,
              "archived": false,
              "type": "heading_2",
              "heading_2": {
                "rich_text": [
                  {
                    "type": "text",
                    "text": {
                      "content": "Lacinato kale is a variety of kale with a long tradition in Italian cuisine...",
                      "link": { "url": "https://en.wikipedia.org/wiki/Lacinato_kale" }
                    },
                    "annotations": { "bold": false, "italic": false, "strikethrough": false, "underline": false, "code": false, "color": "default" },
                    "plain_text": "Lacinato kale is a variety of kale with a long tradition in Italian cuisine...",
                    "href": "https://en.wikipedia.org/wiki/Lacinato_kale"
                  }
                ],
                "color": "default",
                "is_toggleable": false
              }
            },
            {
              "object": "block",
              "id": "acc7eb06-05cd-4603-a384-5e1e4f1f4e72",
              "parent": { "type": "page_id", "page_id": "59833787-2cf9-4fdf-8782-e53db20768a5" },
              "created_time": "2022-03-01T19:05:00.000Z",
              "last_edited_time": "2022-07-06T19:51:00.000Z",
              "created_by": { "object": "user", "id": "ee5f0f84-409a-440f-983a-a5315961c6e4" },
              "last_edited_by": { "object": "user", "id": "0c3e9826-b8f7-4f73-927d-2caaf86f1103" },
              "has_children": false,
              "archived": false,
              "type": "paragraph",
              "paragraph": {
                "rich_text": [
                  {
                    "type": "text",
                    "text": {
                      "content": "Lacinato kale is a variety of kale with a long tradition in Italian cuisine...",
                      "link": { "url": "https://en.wikipedia.org/wiki/Lacinato_kale" }
                    },
                    "annotations": { "bold": false, "italic": false, "strikethrough": false, "underline": false, "code": false, "color": "default" },
                    "plain_text": "Lacinato kale is a variety of kale with a long tradition in Italian cuisine...",
                    "href": "https://en.wikipedia.org/wiki/Lacinato_kale"
                  }
                ],
                "color": "default"
              }
            }
          ],
          "next_cursor": null,
          "has_more": false,
          "type": "block",
          "block": {}
        }
        """.trimIndent()

        val http = notionHttpWith { req ->
            assertEquals(HttpMethod.Get, req.method)
            assertTrue(req.url.fullPath.contains("/blocks/$parentId/children"))
            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val repo: BlockRepository = BlockRepositoryImpl(BlockServiceImpl(http))
        val page: ResultsResponseDto<BlockDto> = repo.listChildren(parentId, null, null)
        assertEquals(false, page.hasMore)
        assertEquals(2, page.results.size)
        assertEquals("heading_2", page.results.first().let { (it as BlockDto.HeadingTwo).heading.let { "heading_2" } })
        println("✅ list_children_ok size=${page.results.size}")
    }

    @Test
    fun update_ok() = runTest {
        val blockId = "c02fc1d3-db8b-45c5-a222-27595b15aea7"
        val req = BlockDto.HeadingTwo(
            id = blockId,
            archived = false,
            createdTime = "2022-03-01T19:05:00.000Z",
            lastEditedTime = "2022-07-06T19:41:00.000Z",
            hasChildren = false,
            heading = BlockDto.HeadingTwo.Value(
                richText = listOf(
                    RichTextDto.Text(
                        data = RichTextDto.Text.TextData(content = "Lacinato kale (updated)"),
                        annotations = RichTextAnnotations(
                            bold = false,
                            italic = false,
                            strikethrough = false,
                            underline = false,
                            code = false,
                            color = RichTextColor.Default
                        ),
                        plainText = "Lacinato kale (updated)",
                        href = null
                    )
                )
            )
        )

        val response = """
        {
          "object": "block",
          "id": "$blockId",
          "parent": { "type": "page_id", "page_id": "59833787-2cf9-4fdf-8782-e53db20768a5" },
          "created_time": "2022-03-01T19:05:00.000Z",
          "last_edited_time": "2022-07-06T19:50:00.000Z",
          "created_by": { "object": "user", "id": "ee5f0f84-409a-440f-983a-a5315961c6e4" },
          "last_edited_by": { "object": "user", "id": "ee5f0f84-409a-440f-983a-a5315961c6e4" },
          "has_children": false,
          "archived": false,
          "type": "heading_2",
          "heading_2": {
            "rich_text": [
              {
                "type": "text",
                "text": { "content": "Lacinato kale (updated)", "link": null },
                "annotations": { "bold": false, "italic": false, "strikethrough": false, "underline": false, "code": false, "color": "default" },
                "plain_text": "Lacinato kale (updated)",
                "href": null
              }
            ],
            "color": "default",
            "is_toggleable": false
          }
        }
        """.trimIndent()

        val http = notionHttpWith { r ->
            assertEquals(HttpMethod.Patch, r.method)
            assertTrue(r.url.fullPath.contains("/blocks/$blockId"))

            val sent = r.body.toByteArray().decodeToString()
            val bodyJson = Json.parseToJsonElement(sent).jsonObject
            val heading = bodyJson["heading_2"]?.jsonObject
            assertNotNull(heading)
            val rt = heading["rich_text"]?.jsonArray
            assertNotNull(rt)
            val content = rt[0].jsonObject["text"]?.jsonObject?.get("content")?.jsonPrimitive?.content
            assertEquals("Lacinato kale (updated)", content)

            respond(
                content = response,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }

        val service: BlockService = BlockServiceImpl(http)
        val repo: BlockRepository = BlockRepositoryImpl(service)

        val updated: NotionBlock = repo.update(blockId, req)
        assertTrue(updated is NotionBlock.HeadingTwo)
        assertEquals(blockId, updated.id)
        println("✅ block update_ok id=${updated.id}")
    }
}
