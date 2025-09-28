package core.data.mapper

import core.data.model.internal.dto.BlockDto
import core.data.model.internal.dto.BlockFileValue
import core.data.model.internal.dto.richtext.RichTextDto
import core.data.model.result.NotionBlock
import core.data.model.result.NotionFile

internal fun BlockDto.toDomain(): NotionBlock = when (this) {
    is BlockDto.Paragraph -> NotionBlock.Paragraph(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = paragraph.text.map(RichTextDto::toDomain),
    )

    is BlockDto.Code -> NotionBlock.Code(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = code.text.map(RichTextDto::toDomain),
        language = code.language,
    )

    is BlockDto.HeadingOne -> NotionBlock.HeadingOne(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = heading.text.map(RichTextDto::toDomain),
    )

    is BlockDto.HeadingTwo -> NotionBlock.HeadingTwo(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = heading.text.map(RichTextDto::toDomain),
    )

    is BlockDto.HeadingThree -> NotionBlock.HeadingThree(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = heading.text.map(RichTextDto::toDomain),
    )

    is BlockDto.BulletedListItem -> NotionBlock.BulletedListItem(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = bulletedListItem.text.map(RichTextDto::toDomain),
    )

    is BlockDto.NumberedListItem -> NotionBlock.NumberedListItem(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = numberedListItem.text.map(RichTextDto::toDomain),
    )

    is BlockDto.ToDo -> NotionBlock.ToDo(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = todo.text.map(RichTextDto::toDomain),
        checked = todo.checked,
    )

    is BlockDto.Toggle -> NotionBlock.Toggle(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = toggle.text.map(RichTextDto::toDomain),
    )

    is BlockDto.ChildPage -> NotionBlock.ChildPage(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        title = childPage.title,
    )

    is BlockDto.ChildDatabase -> NotionBlock.ChildDatabase(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        title = childDatabase.title,
    )

    is BlockDto.Embed -> NotionBlock.Embed(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
    )

    is BlockDto.Image -> NotionBlock.Image(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        file = image.toDomain(),
    )

    is BlockDto.Video -> NotionBlock.Video(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        file = video.toDomain(),
    )

    is BlockDto.File -> NotionBlock.File(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        file = file.toDomain(),
    )

    is BlockDto.Pdf -> NotionBlock.Pdf(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        file = pdf.toDomain(),
    )

    is BlockDto.Bookmark -> NotionBlock.Bookmark(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        caption = bookmark.caption.map(RichTextDto::toDomain),
        url = bookmark.url,
    )

    is BlockDto.Callout -> NotionBlock.Callout(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = callout.text.map(RichTextDto::toDomain),
        icon = callout.iconDto.toDomain(),
    )

    is BlockDto.Quote -> NotionBlock.Quote(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = quote.text.map(RichTextDto::toDomain),
    )

    is BlockDto.Equation -> NotionBlock.Equation(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        expression = equation.expression,
    )

    is BlockDto.Divider -> NotionBlock.Divider(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
    )

    is BlockDto.TableOfContents -> NotionBlock.TableOfContents(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
    )

    is BlockDto.Column -> NotionBlock.Column(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
    )

    is BlockDto.ColumnList -> NotionBlock.ColumnList(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
    )

    is BlockDto.LinkPreview -> NotionBlock.LinkPreview(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        url = linkPreview.url,
    )

    is BlockDto.Unsupported -> NotionBlock.Unsupported(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
    )
}

internal fun BlockFileValue.toDomain(): NotionFile = when {
    file != null -> NotionFile.File(url = file.url, expiryTime = file.expiryTime)
    external != null -> NotionFile.External(url = external.url)
    else -> error("cannot map block file value: $this")
}