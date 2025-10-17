import core.data.model.internal.dto.block.ParentDto
import core.data.model.internal.dto.common.IconDto
import core.data.model.internal.dto.page.PagePropertyDto
import core.data.model.internal.request.page.CreatePageRequest
import core.data.model.internal.request.page.UpdatePageRequest
import core.data.model.result.page.NotionPage
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.JsonObject
import org.junit.Assume.assumeTrue
import org.junit.Test
import repository.page.PageRepository
import repository.page.PageRepositoryImpl
import service.page.PageService
import service.page.PageServiceImpl
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PageLiveTest {

    private fun env(name: String): String? =
        System.getenv(name) ?: System.getProperty(name)

    private fun newHttp(): NotionHttp {
        val token = env("NOTION_TOKEN")
        assumeTrue("NOTION_TOKEN is not set; skipping PageLiveTest", !token.isNullOrBlank())
        return NotionHttp(EnvTokenProvider(token!!), HttpClient())
    }

    private fun repo(): PageRepository {
        val http = newHttp()
        val service: PageService = PageServiceImpl(http)
        return PageRepositoryImpl(service)
    }

    private fun titleProperty(text: String): PagePropertyDto.Title {
        val v = PagePropertyDto.Title.Value(
            type = "text",
            text = PagePropertyDto.Title.Value.Text(content = text, link = null),
            annotations = PagePropertyDto.Title.Value.Annotations(
                bold = false,
                italic = false,
                strikethrough = false,
                underline = false,
                code = false,
                color = "default"
            ),
            plainText = text,
            href = null
        )
        return PagePropertyDto.Title(id = "title", title = listOf(v))
    }

    @Test
    fun create_live_minimal() = runBlocking {
        val dsId = env("NOTION_TEST_DATASOURCE_ID")
        assumeTrue("NOTION_TEST_DATASOURCE_ID is not set; skipping create_live_minimal", !dsId.isNullOrBlank())

        val req = CreatePageRequest(
            parent = ParentDto.DataSourceId(dataSourceId = dsId!!),
            properties = mapOf(
                "title" to titleProperty("SDK-KMP Create Test")
            ),
            icon = IconDto.Emoji("🧪"),
            cover = null
        )

        val created: NotionPage = repo().create(req)
        assertNotNull(created.id)
        assertEquals(false, created.archived)
        println("✅ create_live_minimal: id=${created.id}, url=${created.url}")
    }

    @Test
    fun retrieve_live() = runBlocking {
        val pageId = env("NOTION_TEST_PAGE_ID")
        assumeTrue("NOTION_TEST_PAGE_ID is not set; skipping retrieve_live", !pageId.isNullOrBlank())

        val page = repo().retrieve(pageId!!)
        assertNotNull(page.createdTime)
        assertNotNull(page.lastEditedTime)
        println("✅ retrieve_live: id=${page.id}, titleKeys=${page.properties.keys.joinToString()}")
    }


    @Test
    fun update_title_live() = runBlocking {
        val pageId = env("NOTION_TEST_PAGE_ID")
        assumeTrue("NOTION_TEST_PAGE_ID is not set; skipping update_title_live", !pageId.isNullOrBlank())

        val req = UpdatePageRequest(
            properties = mapOf("title" to titleProperty("SDK-KMP Updated Title")),
            icon = null,
            cover = null,
            archived = null
        )

        val updated = repo().update(pageId!!, req)
        assertEquals(pageId, updated.id)
        println("✅ update_title_live: id=${updated.id}")
    }

    @Test
    fun retrieve_property_item_title_live() = runBlocking {
        val pageId = env("NOTION_TEST_PAGE_ID")
        assumeTrue("NOTION_TEST_PAGE_ID is not set; skipping retrieve_property_item_title_live", !pageId.isNullOrBlank())

        val item: JsonObject = repo().retrievePropertyItem(
            pageId = pageId!!,
            propertyId = "title",
            startCursor = null,
            pageSize = 25
        )
        assertNotNull(item)
        println("✅ retrieve_property_item_title_live: size=${item.size}")
    }
}
