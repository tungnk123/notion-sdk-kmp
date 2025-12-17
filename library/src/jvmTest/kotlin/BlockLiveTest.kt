import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.block.BlockDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.richtext.RichTextAnnotations
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.richtext.RichTextColor
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.richtext.RichTextDto
import io.github.tungnk123.notionsdkkmp.core.data.model.result.block.NotionBlock
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assume.assumeTrue
import org.junit.Test
import io.github.tungnk123.notionsdkkmp.repository.block.BlockRepository
import io.github.tungnk123.notionsdkkmp.repository.block.BlockRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.block.BlockService
import io.github.tungnk123.notionsdkkmp.service.block.BlockServiceImpl
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

    @Test
    fun get_all_children_with_pagination_live() = runBlocking {
        val pageId = env("NOTION_TEST_PAGE_ID")
        assumeTrue("NOTION_TEST_PAGE_ID is not set; skipping get_all_children_with_pagination_live", !pageId.isNullOrBlank())

        val repo = repo()
        val allChildren = repo.getAllChildren(pageId!!)

        assertNotNull(allChildren)
        println("✅ get_all_children_with_pagination_live: total blocks=${allChildren.size}")

        // Print block types
        allChildren.forEachIndexed { index, block ->
            println("  [$index] id=${block.id}, type=${block}, hasChildren=${block.hasChildren}")
        }
    }

    @Test
    fun get_all_children_recursive_live() = runBlocking {
        val pageId = env("NOTION_TEST_PAGE_ID")
        assumeTrue("NOTION_TEST_PAGE_ID is not set; skipping get_all_children_recursive_live", !pageId.isNullOrBlank())

        val repo = repo()
        val allBlocks = repo.getAllChildrenRecursive(pageId!!)

        assertNotNull(allBlocks)
        println("✅ get_all_children_recursive_live: total blocks (including nested)=${allBlocks.size}")
    }

    @Test
    fun get_todo_blocks_status_live() = runBlocking {
        val pageId = env("NOTION_TEST_PAGE_ID")
        assumeTrue("NOTION_TEST_PAGE_ID is not set; skipping get_todo_blocks_status_live", !pageId.isNullOrBlank())

        val repo = repo()
        val allBlocks = repo.getAllChildrenRecursive(pageId!!)

        val todoBlocks = allBlocks.filterIsInstance<NotionBlock.ToDo>()

        println("✅ get_todo_blocks_status_live: found ${todoBlocks.size} todo items")

        val checkedCount = todoBlocks.count { it.checked }
        val uncheckedCount = todoBlocks.count { it.checked == false }

        println("   ✓ Checked: $checkedCount")
        println("   ☐ Unchecked: $uncheckedCount")

        todoBlocks.forEach { todo ->
            val text = todo.richText.firstOrNull()?.plainText ?: "No text"
            val status = if (todo.checked) "✓" else "☐"
            println("   $status $text")
        }
    }

    @Test
    fun measure_nested_blocks_performance_live() = runBlocking {
        val pageId = env("NOTION_TEST_PAGE_ID")
        assumeTrue("NOTION_TEST_PAGE_ID is not set; skipping measure_nested_blocks_performance_live", !pageId.isNullOrBlank())

        val repo = repo()

        // Measure getAllChildren
        val start1 = System.currentTimeMillis()
        val allChildren = repo.getAllChildren(pageId!!)
        val time1 = System.currentTimeMillis() - start1

        // Measure getAllChildrenRecursive
        val start2 = System.currentTimeMillis()
        val allRecursive = repo.getAllChildrenRecursive(pageId)
        val time2 = System.currentTimeMillis() - start2

        println("✅ measure_nested_blocks_performance_live:")
        println("   getAllChildren: ${allChildren.size} blocks in ${time1}ms")
        println("   getAllChildrenRecursive: ${allRecursive.size} blocks in ${time2}ms")
        println("   Ratio: ${allRecursive.size.toFloat() / allChildren.size}x blocks, ${time2.toFloat() / time1}x time")
    }
}