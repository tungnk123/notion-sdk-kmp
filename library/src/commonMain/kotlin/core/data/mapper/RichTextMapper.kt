package core.data.mapper

import core.data.model.internal.obj.RichTextObject
import core.data.model.internal.obj.RichTextAnnotations
import core.data.model.internal.obj.RichTextColor
import core.data.model.internal.obj.RichTextType
import core.data.model.result.NotionRichText
import core.data.model.result.NotionRichTextAnnotations
import core.data.model.result.NotionRichTextColor
import core.data.model.result.NotionRichTextType

internal fun RichTextObject.toDomain(): NotionRichText = when (this) {
    is RichTextObject.TextObject -> NotionRichText.Text(
        plainText = plainText,
        url = href,
        type = type.toDomain(),
        annotations = annotations.toDomain(),
    )

    is RichTextObject.Mention -> when (mention) {
        is RichTextObject.Mention.Value.User -> NotionRichText.Mention.User(
            plainText = plainText,
            url = href,
            type = type.toDomain(),
            annotations = annotations.toDomain(),
            user = mention.userObject.toDomain(),
        )

        is RichTextObject.Mention.Value.Page -> NotionRichText.Mention.Page(
            plainText = plainText,
            url = href,
            type = type.toDomain(),
            annotations = annotations.toDomain(),
            id = mention.page.id,
        )

        is RichTextObject.Mention.Value.Database -> NotionRichText.Mention.Database(
            plainText = plainText,
            url = href,
            type = type.toDomain(),
            annotations = annotations.toDomain(),
            id = mention.database.id,
        )

        is RichTextObject.Mention.Value.Date -> NotionRichText.Mention.Date(
            plainText = plainText,
            url = href,
            type = type.toDomain(),
            annotations = annotations.toDomain(),
            start = mention.date.start,
            end = mention.date.end,
        )

        RichTextObject.Mention.Value.LinkPreview -> NotionRichText.Mention.LinkPreview(
            plainText = plainText,
            url = href,
            type = type.toDomain(),
            annotations = annotations.toDomain(),
        )
    }

    is RichTextObject.Equation -> NotionRichText.Equation(
        plainText = plainText,
        url = href,
        type = type.toDomain(),
        annotations = annotations.toDomain(),
        expression = equation.expression,
    )
}

internal fun RichTextType.toDomain(): NotionRichTextType = when (this) {
    RichTextType.Text -> NotionRichTextType.Text
    RichTextType.Mention -> NotionRichTextType.Mention
    RichTextType.Equation -> NotionRichTextType.Equation
}

internal fun RichTextAnnotations.toDomain(): NotionRichTextAnnotations = NotionRichTextAnnotations(
    bold = bold,
    italic = italic,
    strikethrough = strikethrough,
    underline = underline,
    code = code,
    color = color.toDomain(),
)

internal fun RichTextColor.toDomain(): NotionRichTextColor = when (this) {
    RichTextColor.Default -> NotionRichTextColor.Default
    RichTextColor.Gray -> NotionRichTextColor.Gray
    RichTextColor.Brown -> NotionRichTextColor.Brown
    RichTextColor.Orange -> NotionRichTextColor.Orange
    RichTextColor.Yellow -> NotionRichTextColor.Yellow
    RichTextColor.Green -> NotionRichTextColor.Green
    RichTextColor.Blue -> NotionRichTextColor.Blue
    RichTextColor.Purple -> NotionRichTextColor.Purple
    RichTextColor.Pink -> NotionRichTextColor.Pink
    RichTextColor.Red -> NotionRichTextColor.Red
    RichTextColor.GrayBackground -> NotionRichTextColor.GrayBackground
    RichTextColor.BrownBackground -> NotionRichTextColor.BrownBackground
    RichTextColor.OrangeBackground -> NotionRichTextColor.OrangeBackground
    RichTextColor.YellowBackground -> NotionRichTextColor.YellowBackground
    RichTextColor.GreenBackground -> NotionRichTextColor.GreenBackground
    RichTextColor.BlueBackground -> NotionRichTextColor.BlueBackground
    RichTextColor.PurpleBackground -> NotionRichTextColor.PurpleBackground
    RichTextColor.PinkBackground -> NotionRichTextColor.PinkBackground
    RichTextColor.RedBackground -> NotionRichTextColor.RedBackground
}
