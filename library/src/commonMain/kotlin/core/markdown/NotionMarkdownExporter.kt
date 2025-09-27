package core.markdown

import notion.Notion
import core.data.model.result.NotionBlock
import kotlin.jvm.JvmStatic

@Suppress("KDocUnresolvedReference")
interface NotionMarkdownExporter {

    /**
     * Convert a [block] to a simple Markdown string, without many features like HTML blocks, etc.
     */
    fun export(
        blocks: List<NotionBlock>,
        settings: Settings = Settings(),
    ): String

    /**
     * Convert a [block] to a simple Markdown string, without many features like HTML blocks, etc.
     * Retrieve the children blocks as well.
     *
     * @param depthLevel How many times to retrieve the children blocks
     */
    suspend fun exportRecursively(
        blocks: List<NotionBlock>,
        settings: Settings = Settings(),
        notion: Notion,
        depthLevel: Int = DEPTH_LEVEL_NO,
    ): String

    /**
     * @property todoCheckedPrefix Default is emoji, the simplest option. Might be `- [x]` for GFM
     * @property todoUncheckedPrefix Default is emoji, the simplest option. Might be `- [ ]` for GFM
     * @property todoCheckedStrikethrough Format checked to_do items' text as strikethrough
     * @property addExpiryNoticeForInternalFiles Internal (hosted) Notion files have expiry time. If enabled, a note will be added before such blocks.
     * @property formatEquationAsCode Self-explanatory
     */
    data class Settings(
        val todoCheckedPrefix: String = "☑",
        val todoUncheckedPrefix: String = "☐",
        val todoCheckedStrikethrough: Boolean = true,
        val addExpiryNoticeForInternalFiles: Boolean = true,
        val formatEquationAsCode: Boolean = true,
    )

    companion object {
        const val DEPTH_LEVEL_NO: Int = 0

        @JvmStatic
        fun create(): NotionMarkdownExporter =
            NotionMarkdownExporterImpl()
    }
}