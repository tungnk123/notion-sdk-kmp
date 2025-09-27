package core.data.model.internal.obj

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal sealed class BlockObject {
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
    internal data class Paragraph(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val paragraph: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(
            val text: List<RichTextObject>,
        )
    }

    @SerialName("code")
    @Serializable
    internal data class Code(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val code: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(
            val text: List<RichTextObject>,
            val language: String,
        )
    }

    @SerialName("heading_1")
    @Serializable
    internal data class HeadingOne(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        @SerialName("heading_1") val heading: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(
            val text: List<RichTextObject>,
        )
    }

    @SerialName("heading_2")
    @Serializable
    internal data class HeadingTwo(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        @SerialName("heading_2") val heading: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(
            val text: List<RichTextObject>,
        )
    }

    @SerialName("heading_3")
    @Serializable
    internal data class HeadingThree(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        @SerialName("heading_3") val heading: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(
            val text: List<RichTextObject>,
        )
    }

    @SerialName("bulleted_list_item")
    @Serializable
    internal data class BulletedListItem(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        @SerialName("bulleted_list_item") val bulletedListItem: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(
            val text: List<RichTextObject>,
        )
    }

    @SerialName("numbered_list_item")
    @Serializable
    internal data class NumberedListItem(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        @SerialName("numbered_list_item") val numberedListItem: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(
            val text: List<RichTextObject>,
        )
    }

    @SerialName("to_do")
    @Serializable
    internal data class ToDo(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        @SerialName("to_do") val todo: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(
            val text: List<RichTextObject>,
            val checked: Boolean,
        )
    }

    @SerialName("toggle")
    @Serializable
    internal data class Toggle(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val toggle: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(
            val text: List<RichTextObject>,
        )
    }

    @SerialName("child_page")
    @Serializable
    internal data class ChildPage(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
        @SerialName("child_page") val childPage: Value,
    ) : BlockObject() {
        @Serializable
        internal data class Value(
            val title: String,
        )
    }

    @SerialName("child_database")
    @Serializable
    internal data class ChildDatabase(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
        @SerialName("child_database") val childDatabase: Value,
    ) : BlockObject() {
        @Serializable
        internal data class Value(
            val title: String,
        )
    }

    // todo: actually handled by "bookmark"
    @SerialName("embed")
    @Serializable
    internal data class Embed(
        override val id: String,
        override val archived: Boolean,
        override val createdTime: String,
        override val lastEditedTime: String,
        override val hasChildren: Boolean,
    ) : BlockObject()

    @SerialName("image")
    @Serializable
    internal data class Image(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val image: BlockFileValue,
    ) : BlockObject()

    @SerialName("video")
    @Serializable
    internal data class Video(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val video: BlockFileValue,
    ) : BlockObject()

    @SerialName("file")
    @Serializable
    internal data class File(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val file: BlockFileValue,
    ) : BlockObject()

    @SerialName("pdf")
    @Serializable
    internal data class Pdf(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val pdf: BlockFileValue,
    ) : BlockObject()

    @SerialName("bookmark")
    @Serializable
    internal data class Bookmark(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val bookmark: Value,
    ) : BlockObject() {
        @Serializable
        internal data class Value(
            val caption: List<RichTextObject>,
            val url: String,
        )
    }

    @SerialName("callout")
    @Serializable
    internal data class Callout(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val callout: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(
            val text: List<RichTextObject>,
            val iconObject: IconObject,
        )
    }

    @SerialName("quote")
    @Serializable
    internal data class Quote(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val quote: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(
            val text: List<RichTextObject>,
        )
    }

    @SerialName("equation")
    @Serializable
    internal data class Equation(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        val equation: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(val expression: String)
    }

    @SerialName("divider")
    @Serializable
    internal data class Divider(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
    ) : BlockObject()

    @SerialName("table_of_contents")
    @Serializable
    internal data class TableOfContents(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
    ) : BlockObject()

    @SerialName("column")
    @Serializable
    internal data class Column(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
    ) : BlockObject()

    @SerialName("column_list")
    @Serializable
    internal data class ColumnList(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
    ) : BlockObject()

    @SerialName("link_preview")
    @Serializable
    internal data class LinkPreview(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,

        @SerialName("link_preview") val linkPreview: Value,
    ) : BlockObject() {

        @Serializable
        internal data class Value(
            val url: String,
        )
    }

    @SerialName("unsupported")
    @Serializable
    internal data class Unsupported(
        override val id: String,
        override val archived: Boolean,
        @SerialName("created_time") override val createdTime: String,
        @SerialName("last_edited_time") override val lastEditedTime: String,
        @SerialName("has_children") override val hasChildren: Boolean,
    ) : BlockObject()
}

@Serializable
internal data class BlockFileValue(
    val caption: List<RichTextObject>,
    val file: FileInternal? = null,
    val external: FileExternal? = null,
) {
    @Serializable
    internal data class FileInternal(
        val url: String,
        @SerialName("expiry_time") val expiryTime: String,
    )

    @Serializable
    internal data class FileExternal(
        val url: String,
    )
}