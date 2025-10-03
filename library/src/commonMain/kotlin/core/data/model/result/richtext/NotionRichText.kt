package core.data.model.result.richtext

import core.data.model.result.user.NotionUser
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class NotionRichText {
    @SerialName("plain_text")
    abstract val plainText: String
    abstract val url: String?
    abstract val annotations: NotionRichTextAnnotations

    @Serializable
    @SerialName("text")
    data class Text(
        @SerialName("plain_text")
        override val plainText: String,
        override val url: String?,
        override val annotations: NotionRichTextAnnotations,
    ) : NotionRichText()

    @Serializable
    @SerialName("mention")
    sealed class Mention : NotionRichText() {

        @Serializable
        @SerialName("user")
        data class User(
            @SerialName("plain_text")
            override val plainText: String,
            override val url: String?,
            override val annotations: NotionRichTextAnnotations,

            val user: NotionUser,
        ) : Mention()

        @Serializable
        @SerialName("page")
        data class Page(
            @SerialName("plain_text")
            override val plainText: String,
            override val url: String?,
            override val annotations: NotionRichTextAnnotations,

            val id: String,
        ) : Mention()

        @Serializable
        @SerialName("database")
        data class Database(
            @SerialName("plain_text")
            override val plainText: String,
            override val url: String?,
            override val annotations: NotionRichTextAnnotations,

            val id: String,
        ) : Mention()

        @Serializable
        @SerialName("date")
        data class Date(
            @SerialName("plain_text")
            override val plainText: String,
            override val url: String?,
            override val annotations: NotionRichTextAnnotations,

            val start: String? = null,
            val end: String? = null,
        ) : Mention()

        // todo: broken api example provided
        @Serializable
        @SerialName("link_preview")
        data class LinkPreview(
            @SerialName("plain_text")
            override val plainText: String,
            override val url: String?,
            override val annotations: NotionRichTextAnnotations,
        ) : Mention()
    }

    @Serializable
    @SerialName("equation")
    data class Equation(
        @SerialName("plain_text")
        override val plainText: String,
        override val url: String?,
        override val annotations: NotionRichTextAnnotations,

        val expression: String,
    ) : NotionRichText()
}

@Serializable
enum class NotionRichTextType {
    @SerialName("text")
    Text,

    @SerialName("mention")
    Mention,

    @SerialName("equation")
    Equation;
}

@Serializable
data class NotionRichTextAnnotations(
    val bold: Boolean,
    val italic: Boolean,
    val strikethrough: Boolean,
    val underline: Boolean,
    val code: Boolean,
    val color: NotionRichTextColor,
)

@Serializable
enum class NotionRichTextColor {
    @SerialName("default")
    Default,

    @SerialName("gray")
    Gray,

    @SerialName("brown")
    Brown,

    @SerialName("orange")
    Orange,

    @SerialName("yellow")
    Yellow,

    @SerialName("green")
    Green,

    @SerialName("blue")
    Blue,

    @SerialName("purple")
    Purple,

    @SerialName("pink")
    Pink,

    @SerialName("red")
    Red,

    @SerialName("gray_background")
    GrayBackground,

    @SerialName("brown_background")
    BrownBackground,

    @SerialName("orange_background")
    OrangeBackground,

    @SerialName("yellow_background")
    YellowBackground,

    @SerialName("green_background")
    GreenBackground,

    @SerialName("blue_background")
    BlueBackground,

    @SerialName("purple_background")
    PurpleBackground,

    @SerialName("pink_background")
    PinkBackground,

    @SerialName("red_background")
    RedBackground,
}