package io.github.tungnk123.notionsdkkmp.core.data.mapper

import io.github.tungnk123.notionsdkkmp.core.data.mapper.toDomain
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.richtext.RichTextAnnotations
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.richtext.RichTextColor
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.richtext.RichTextDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.richtext.RichTextType
import io.github.tungnk123.notionsdkkmp.core.data.model.result.richtext.NotionRichText
import io.github.tungnk123.notionsdkkmp.core.data.model.result.richtext.NotionRichTextAnnotations
import io.github.tungnk123.notionsdkkmp.core.data.model.result.richtext.NotionRichTextColor
import io.github.tungnk123.notionsdkkmp.core.data.model.result.richtext.NotionRichTextType

fun RichTextDto.toDomain(): NotionRichText = when (this) {
    is RichTextDto.Text -> NotionRichText.Text(
        plainText = plainText,
        url = href,
        annotations = annotations.toDomain()
    )

    is RichTextDto.Mention -> when (val value = mention) {
        is RichTextDto.Mention.Value.User -> NotionRichText.Mention.User(
            plainText = plainText,
            url = href,
            annotations = annotations.toDomain(),
            user = value.user.toDomain()
        )

        is RichTextDto.Mention.Value.Page -> NotionRichText.Mention.Page(
            plainText = plainText,
            url = href,
            annotations = annotations.toDomain(),
            id = value.page.id
        )

        is RichTextDto.Mention.Value.Database -> NotionRichText.Mention.Database(
            plainText = plainText,
            url = href,
            annotations = annotations.toDomain(),
            id = value.database.id
        )

        is RichTextDto.Mention.Value.Date -> NotionRichText.Mention.Date(
            plainText = plainText,
            url = href,
            annotations = annotations.toDomain(),
            start = value.date.start,
            end = value.date.end
        )

        is RichTextDto.Mention.Value.LinkPreview -> NotionRichText.Mention.LinkPreview(
            plainText = plainText, url = href, annotations = annotations.toDomain()
        )
    }

    is RichTextDto.Equation -> NotionRichText.Equation(
        plainText = plainText,
        url = href,
        annotations = annotations.toDomain(),
        expression = equation.expression
    )
}

fun RichTextType.toDomain(): NotionRichTextType = when (this) {
    RichTextType.Text -> NotionRichTextType.Text
    RichTextType.Mention -> NotionRichTextType.Mention
    RichTextType.Equation -> NotionRichTextType.Equation
}

fun RichTextAnnotations.toDomain(): NotionRichTextAnnotations =
    NotionRichTextAnnotations(
        bold = bold,
        italic = italic,
        strikethrough = strikethrough,
        underline = underline,
        code = code,
        color = color.toDomain()
    )

fun RichTextColor.toDomain(): NotionRichTextColor = when (this) {
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