package core.data.mapper

import core.data.model.internal.obj.IconObject
import core.data.model.result.NotionIcon

internal fun IconObject.toDomain(): NotionIcon = when {
    emoji != null -> NotionIcon.Emoji(emoji)
    file != null -> NotionIcon.File(file.url, file.expiryTime)
    else -> error("$this is not a valid Notion icon")
}