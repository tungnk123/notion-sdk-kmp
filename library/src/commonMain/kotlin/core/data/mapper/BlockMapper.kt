package core.data.mapper

import core.data.model.internal.dto.block.BlockDto
import core.data.model.internal.dto.block.BlockFileValue
import core.data.model.internal.dto.richtext.RichTextDto
import core.data.model.result.block.NotionBlock
import core.data.model.result.common.NotionFile

internal fun BlockDto.toDomain(): NotionBlock = when (this) {
    is BlockDto.Paragraph -> NotionBlock.Paragraph(
        id, archived, createdTime, lastEditedTime, hasChildren,
        richText = paragraph.richText.map(RichTextDto::toDomain),
        color = paragraph.color
    )

    is BlockDto.Code -> NotionBlock.Code(
        id, archived, createdTime, lastEditedTime, hasChildren,
        richText = code.richText.map(RichTextDto::toDomain),
        language = code.language,
        caption = code.caption.map(RichTextDto::toDomain)
    )

    is BlockDto.HeadingOne -> NotionBlock.HeadingOne(
        id, archived, createdTime, lastEditedTime, hasChildren,
        richText = heading.richText.map(RichTextDto::toDomain),
        color = heading.color,
        isToggleable = heading.isToggleable
    )

    is BlockDto.HeadingTwo -> NotionBlock.HeadingTwo(
        id, archived, createdTime, lastEditedTime, hasChildren,
        richText = heading.richText.map(RichTextDto::toDomain),
        color = heading.color,
        isToggleable = heading.isToggleable
    )

    is BlockDto.HeadingThree -> NotionBlock.HeadingThree(
        id, archived, createdTime, lastEditedTime, hasChildren,
        richText = heading.richText.map(RichTextDto::toDomain),
        color = heading.color,
        isToggleable = heading.isToggleable
    )

    is BlockDto.BulletedListItem -> NotionBlock.BulletedListItem(
        id, archived, createdTime, lastEditedTime, hasChildren,
        richText = bulletedListItem.richText.map(RichTextDto::toDomain),
        color = bulletedListItem.color
    )

    is BlockDto.NumberedListItem -> NotionBlock.NumberedListItem(
        id, archived, createdTime, lastEditedTime, hasChildren,
        richText = numberedListItem.richText.map(RichTextDto::toDomain),
        color = numberedListItem.color
    )

    is BlockDto.ToDo -> NotionBlock.ToDo(
        id, archived, createdTime, lastEditedTime, hasChildren,
        richText = todo.richText.map(RichTextDto::toDomain),
        checked = todo.checked,
        color = todo.color
    )

    is BlockDto.Toggle -> NotionBlock.Toggle(
        id, archived, createdTime, lastEditedTime, hasChildren,
        richText = toggle.richText.map(RichTextDto::toDomain),
        color = toggle.color
    )

    is BlockDto.Quote -> NotionBlock.Quote(
        id, archived, createdTime, lastEditedTime, hasChildren,
        richText = quote.richText.map(RichTextDto::toDomain),
        color = quote.color
    )

    is BlockDto.Callout -> NotionBlock.Callout(
        id, archived, createdTime, lastEditedTime, hasChildren,
        richText = callout.richText.map(RichTextDto::toDomain),
        icon = callout.iconDto?.toDomain(),
        color = callout.color
    )

    is BlockDto.ChildPage -> NotionBlock.ChildPage(
        id, archived, createdTime, lastEditedTime, hasChildren,
        title = childPage.title
    )

    is BlockDto.ChildDatabase -> NotionBlock.ChildDatabase(
        id, archived, createdTime, lastEditedTime, hasChildren,
        title = childDatabase.title
    )

    is BlockDto.Embed -> NotionBlock.Embed(
        id, archived, createdTime, lastEditedTime, hasChildren,
        url = embed.url,
        caption = embed.caption.map(RichTextDto::toDomain)
    )

    is BlockDto.Image -> NotionBlock.Image(
        id, archived, createdTime, lastEditedTime, hasChildren,
        file = image.toDomain()
    )

    is BlockDto.Video -> NotionBlock.Video(
        id, archived, createdTime, lastEditedTime, hasChildren,
        file = video.toDomain()
    )

    is BlockDto.File -> NotionBlock.File(
        id, archived, createdTime, lastEditedTime, hasChildren,
        file = file.toDomain()
    )

    is BlockDto.Pdf -> NotionBlock.Pdf(
        id, archived, createdTime, lastEditedTime, hasChildren,
        file = pdf.toDomain()
    )

    is BlockDto.Bookmark -> NotionBlock.Bookmark(
        id, archived, createdTime, lastEditedTime, hasChildren,
        url = bookmark.url,
        caption = bookmark.caption.map(RichTextDto::toDomain)
    )

    is BlockDto.Equation -> NotionBlock.Equation(
        id, archived, createdTime, lastEditedTime, hasChildren,
        expression = equation.expression
    )

    is BlockDto.Divider -> NotionBlock.Divider(
        id, archived, createdTime, lastEditedTime, hasChildren
    )

    is BlockDto.TableOfContents -> NotionBlock.TableOfContents(
        id, archived, createdTime, lastEditedTime, hasChildren,
        color = color
    )

    is BlockDto.Column -> NotionBlock.Column(
        id, archived, createdTime, lastEditedTime, hasChildren
    )

    is BlockDto.ColumnList -> NotionBlock.ColumnList(
        id, archived, createdTime, lastEditedTime, hasChildren
    )

    is BlockDto.LinkPreview -> NotionBlock.LinkPreview(
        id, archived, createdTime, lastEditedTime, hasChildren,
        url = linkPreview.url
    )

    is BlockDto.Unsupported -> NotionBlock.Unsupported(
        id, archived, createdTime, lastEditedTime, hasChildren
    )

    is BlockDto.SyncedBlock -> NotionBlock.SyncedBlock(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        syncedFromBlockId = synced.syncedFrom?.blockId
    )

    is BlockDto.Table -> NotionBlock.Table(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        tableWidth = table.tableWidth,
        hasColumnHeader = table.hasColumnHeader,
        hasRowHeader = table.hasRowHeader
    )

    is BlockDto.TableRow -> NotionBlock.TableRow(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        cells = tableRow.cells.map { cellList -> cellList.map { it.toDomain() } }
    )

    is BlockDto.Template -> NotionBlock.Template(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren,
        richText = template.richText.map(RichTextDto::toDomain)
    )

    is BlockDto.Breadcrumb -> NotionBlock.Breadcrumb(
        id = id,
        archived = archived,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        hasChildren = hasChildren
    )
}

internal fun BlockFileValue.toDomain(): NotionFile = when {
    file != null -> NotionFile.File(url = file.url, expiryTime = file.expiryTime)
    external != null -> NotionFile.External(url = external.url)
    else -> error("cannot map block file value: $this")
}