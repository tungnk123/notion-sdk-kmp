package core.data.mapper

import core.data.model.internal.dto.common.IconDto
import core.data.model.result.common.NotionIcon

internal fun IconDto.toDomain(): NotionIcon = when (this) {
    is IconDto.Emoji -> NotionIcon.Emoji(emoji)
    is IconDto.File -> NotionIcon.File(file.url, file.expiryTime)
    is IconDto.External -> NotionIcon.External(external.url)
}