package core.data.mapper

import core.data.model.internal.obj.BlockObject
import core.data.model.internal.obj.BlockFileValue
import core.data.model.internal.obj.RichTextObject
import core.data.model.result.NotionBlock
import core.data.model.result.NotionFile

internal fun BlockObject.toDomain(): NotionBlock = when (this) {
    is BlockObject.Paragraph -> NotionBlock.Paragraph(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = paragraph.text.map(RichTextObject::toDomain),
    )

    is BlockObject.Code -> NotionBlock.Code(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = code.text.map(RichTextObject::toDomain),
        language = code.language,
    )

    is BlockObject.HeadingOne -> NotionBlock.HeadingOne(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = heading.text.map(RichTextObject::toDomain),
    )

    is BlockObject.HeadingTwo -> NotionBlock.HeadingTwo(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = heading.text.map(RichTextObject::toDomain),
    )

    is BlockObject.HeadingThree -> NotionBlock.HeadingThree(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = heading.text.map(RichTextObject::toDomain),
    )

    is BlockObject.BulletedListItem -> NotionBlock.BulletedListItem(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = bulletedListItem.text.map(RichTextObject::toDomain),
    )

    is BlockObject.NumberedListItem -> NotionBlock.NumberedListItem(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = numberedListItem.text.map(RichTextObject::toDomain),
    )

    is BlockObject.ToDo -> NotionBlock.ToDo(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = todo.text.map(RichTextObject::toDomain),
        checked = todo.checked,
    )

    is BlockObject.Toggle -> NotionBlock.Toggle(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = toggle.text.map(RichTextObject::toDomain),
    )

    is BlockObject.ChildPage -> NotionBlock.ChildPage(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        title = childPage.title,
    )

    is BlockObject.ChildDatabase -> NotionBlock.ChildDatabase(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        title = childDatabase.title,
    )

    is BlockObject.Embed -> NotionBlock.Embed(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
    )

    is BlockObject.Image -> NotionBlock.Image(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        file = image.toDomain(),
    )

    is BlockObject.Video -> NotionBlock.Video(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        file = video.toDomain(),
    )

    is BlockObject.File -> NotionBlock.File(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        file = file.toDomain(),
    )

    is BlockObject.Pdf -> NotionBlock.Pdf(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        file = pdf.toDomain(),
    )

    is BlockObject.Bookmark -> NotionBlock.Bookmark(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        caption = bookmark.caption.map(RichTextObject::toDomain),
        url = bookmark.url,
    )

    is BlockObject.Callout -> NotionBlock.Callout(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = callout.text.map(RichTextObject::toDomain),
        icon = callout.iconObject.toDomain(),
    )

    is BlockObject.Quote -> NotionBlock.Quote(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        text = quote.text.map(RichTextObject::toDomain),
    )

    is BlockObject.Equation -> NotionBlock.Equation(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        expression = equation.expression,
    )

    is BlockObject.Divider -> NotionBlock.Divider(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
    )

    is BlockObject.TableOfContents -> NotionBlock.TableOfContents(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
    )

    is BlockObject.Column -> NotionBlock.Column(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
    )

    is BlockObject.ColumnList -> NotionBlock.ColumnList(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
    )

    is BlockObject.LinkPreview -> NotionBlock.LinkPreview(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        url = linkPreview.url,
    )

    is BlockObject.Unsupported -> NotionBlock.Unsupported(
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