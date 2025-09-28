package core.data.model.internal.dto.richtext

import core.data.model.internal.dto.UserDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class RichTextDto {
    @SerialName("plain_text")
    abstract val plainText: String
    abstract val href: String?
    abstract val type: RichTextType
    abstract val annotations: RichTextAnnotations

    @Serializable
    @SerialName("text")
    data class Text(
        @SerialName("plain_text") override val plainText: String,
        override val href: String? = null,
        override val type: RichTextType = RichTextType.Text,
        override val annotations: RichTextAnnotations,
        val text: TextData
    ) : RichTextDto() {
        @Serializable
        data class TextData(val content: String, val link: Link? = null)
        @Serializable
        data class Link(val url: String? = null)
    }

    @Serializable
    @SerialName("mention")
    data class Mention(
        @SerialName("plain_text") override val plainText: String,
        override val href: String? = null,
        override val type: RichTextType = RichTextType.Mention,
        override val annotations: RichTextAnnotations,
        val mention: Value
    ) : RichTextDto() {
        @Serializable
        sealed class Value {
            @Serializable
            @SerialName("user")
            data class User(val user: UserDto) : Value()
            @Serializable
            @SerialName("page")
            data class Page(val page: Id) : Value()
            @Serializable
            @SerialName("database")
            data class Database(val database: Id) : Value()
            @Serializable
            @SerialName("date")
            data class Date(val date: DateRange) : Value()
            @Serializable
            @SerialName("link_preview")
            data class LinkPreview(val linkPreview: LinkPreviewValue = LinkPreviewValue()) : Value()
        }

        @Serializable
        data class Id(val id: String)
        @Serializable
        data class DateRange(
            val start: String? = null,
            val end: String? = null,
            @SerialName("time_zone") val timeZone: String? = null
        )

        @Serializable
        data class LinkPreviewValue(val url: String? = null)
    }

    @Serializable
    @SerialName("equation")
    data class Equation(
        @SerialName("plain_text") override val plainText: String,
        override val href: String? = null,
        override val type: RichTextType = RichTextType.Equation,
        override val annotations: RichTextAnnotations,
        val equation: Expression
    ) : RichTextDto() {
        @Serializable
        data class Expression(val expression: String)
    }
}

@Serializable
enum class RichTextType {
    @SerialName("text")
    Text,
    @SerialName("mention")
    Mention,
    @SerialName("equation")
    Equation
}

@Serializable
data class RichTextAnnotations(
    val bold: Boolean,
    val italic: Boolean,
    val strikethrough: Boolean,
    val underline: Boolean,
    val code: Boolean,
    val color: RichTextColor
)

@Serializable
enum class RichTextColor {
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
    RedBackground
}
