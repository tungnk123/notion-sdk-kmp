package core.data.mapper

import core.data.model.internal.obj.Icon
import core.data.model.result.NotionIcon

internal fun Icon.toDomain(): NotionIcon = when {
    emoji != null -> NotionIcon.Emoji(emoji)
    file != null -> NotionIcon.File(file.url, file.expiryTime)
    else -> error("$this is not a valid Notion icon")
}