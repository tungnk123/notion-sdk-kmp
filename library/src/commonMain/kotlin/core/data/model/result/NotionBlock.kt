package core.data.model.result

import core.data.model.result.richtext.NotionRichText
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class NotionBlock {
    abstract val id: String
    abstract val archived: Boolean

    @SerialName("created_time")
    abstract val createdTime: String

    @SerialName("last_edited_time")
    abstract val lastEditedTime: String

    @SerialName("has_children")
    abstract val hasChildren: Boolean

    @SerialName("paragraph")
    @Serializable
    data class Paragraph(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("code")
    @Serializable
    data class Code(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val text: List<NotionRichText>,
        val language: String,
    ) : NotionBlock()

    @SerialName("heading_1")
    @Serializable
    data class HeadingOne(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("heading_2")
    @Serializable
    data class HeadingTwo(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("heading_3")
    @Serializable
    data class HeadingThree(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("bulleted_list_item")
    @Serializable
    data class BulletedListItem(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("numbered_list_item")
    @Serializable
    data class NumberedListItem(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("to_do")
    @Serializable
    data class ToDo(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val text: List<NotionRichText>,
        val checked: Boolean,
    ) : NotionBlock()

    @SerialName("toggle")
    @Serializable
    data class Toggle(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("child_page")
    @Serializable
    data class ChildPage(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val title: String,
    ) : NotionBlock()

    @SerialName("child_database")
    @Serializable
    data class ChildDatabase(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val title: String,
    ) : NotionBlock()

    // todo: actually handled by "bookmark"
    @SerialName("embed")
    @Serializable
    data class Embed(
        override val id: String,
        override val archived: Boolean,
        override val createdTime: String,
        override val lastEditedTime: String,
        override val hasChildren: Boolean,
    ) : NotionBlock()

    @SerialName("image")
    @Serializable
    data class Image(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
        override val file: NotionFile,
    ) : NotionBlock(), NotionFileBlock

    @SerialName("video")
    @Serializable
    data class Video(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
        override val file: NotionFile,
    ) : NotionBlock(), NotionFileBlock

    @SerialName("file")
    @Serializable
    data class File(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
        override val file: NotionFile,
    ) : NotionBlock(), NotionFileBlock

    @SerialName("pdf")
    @Serializable
    data class Pdf(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
        override val file: NotionFile,
    ) : NotionBlock(), NotionFileBlock

    @SerialName("bookmark")
    @Serializable
    data class Bookmark(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val caption: List<NotionRichText>,
        val url: String,
    ) : NotionBlock()

    @SerialName("callout")
    @Serializable
    data class Callout(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val text: List<NotionRichText>,
        val icon: NotionIcon,
    ) : NotionBlock()

    @SerialName("quote")
    @Serializable
    data class Quote(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
        val text: List<NotionRichText>,
    ) : NotionBlock()

    @SerialName("equation")
    @Serializable
    data class Equation(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val expression: String,
    ) : NotionBlock()

    @SerialName("divider")
    @Serializable
    data class Divider(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
    ) : NotionBlock()

    @SerialName("table_of_contents")
    @Serializable
    data class TableOfContents(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
    ) : NotionBlock()

    @SerialName("column")
    @Serializable
    data class Column(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
    ) : NotionBlock()

    @SerialName("column_list")
    @Serializable
    data class ColumnList(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
    ) : NotionBlock()

    @SerialName("link_preview")
    @Serializable
    data class LinkPreview(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
        val url: String,
    ) : NotionBlock()

    @SerialName("unsupported")
    @Serializable
    data class Unsupported(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
    ) : NotionBlock()
}

interface NotionFileBlock {
    val file: NotionFile
}