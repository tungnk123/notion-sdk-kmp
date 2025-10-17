import core.data.model.internal.dto.block.BlockDto
import core.data.model.internal.dto.richtext.RichTextAnnotations
import core.data.model.internal.dto.richtext.RichTextColor
import core.data.model.internal.dto.richtext.RichTextDto
import core.data.model.result.block.NotionBlock
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assume.assumeTrue
import org.junit.Test
import repository.block.BlockRepository
import repository.block.BlockRepositoryImpl
import service.block.BlockService
import service.block.BlockServiceImpl
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class BlockLiveTest {

    private fun env(name: String): String? =
        System.getenv(name) ?: System.getProperty(name)

    private fun newHttp(): NotionHttp {
        val token = env("NOTION_TOKEN")
        assumeTrue("NOTION_TOKEN is not set; skipping BlockLiveTest", !token.isNullOrBlank())
        return NotionHttp(EnvTokenProvider(token!!), HttpClient())
    }

    private fun repo(): BlockRepository {
        val http = newHttp()
        val service: BlockService = BlockServiceImpl(http)
        return BlockRepositoryImpl(service)
    }

    private fun annotationsDefault(): RichTextAnnotations =
        RichTextAnnotations(
            bold = false,
            italic = false,
            strikethrough = false,
            underline = false,
            code = false,
            color = RichTextColor.Default
        )

    @Test
    fun retrieve_block_live() = runBlocking {
        val blockId = env("NOTION_TEST_BLOCK_ID")
        assumeTrue("NOTION_TEST_BLOCK_ID is not set; skipping retrieve_block_live", !blockId.isNullOrBlank())

        val r = repo().retrieve(blockId!!)
        assertNotNull(r.id)
        println("✅ retrieve_block_live: id=${r.id}, type=${r::class.simpleName}")
    }

    @Test
    fun list_children_live() = runBlocking {
        val parentId = env("NOTION_TEST_PAGE_ID")
        assumeTrue("NOTION_TEST_PAGE_ID is not set; skipping list_children_live", !parentId.isNullOrBlank())

        val http = newHttp()
        val repo = BlockRepositoryImpl(BlockServiceImpl(http))
        val res = repo.listChildren(parentId!!, null, 20)
        assertNotNull(res)
        println("✅ list_children_live: size=${res.results.size}, hasMore=${res.hasMore}")
    }

    @Test
    fun update_heading2_live() = runBlocking {
        val blockId = env("NOTION_TEST_BLOCK_ID")
        assumeTrue("NOTION_TEST_BLOCK_ID is not set; skipping update_heading2_live", !blockId.isNullOrBlank())

        val http = newHttp()
        val service: BlockService = BlockServiceImpl(http)
        val repo: BlockRepository = BlockRepositoryImpl(service)

        val existing: BlockDto = service.retrieve(blockId!!)
        val rt = listOf(
            RichTextDto.Text(
                plainText = "SDK-KMP Heading Updated",
                href = null,
                annotations = annotationsDefault(),
                data = RichTextDto.Text.TextData(content = "SDK-KMP Heading Updated")
            )
        )

        val req = BlockDto.HeadingTwo(
            id = existing.id,
            archived = existing.archived,
            createdTime = existing.createdTime,
            lastEditedTime = existing.lastEditedTime,
            hasChildren = existing.hasChildren,
            heading = BlockDto.HeadingTwo.Value(richText = rt)
        )

        val updated: NotionBlock = repo.update(blockId, req)
        assertTrue(updated is NotionBlock.HeadingTwo)
        assertEquals(blockId, updated.id)
        println("✅ update_heading2_live: id=${updated.id}")
    }
}
