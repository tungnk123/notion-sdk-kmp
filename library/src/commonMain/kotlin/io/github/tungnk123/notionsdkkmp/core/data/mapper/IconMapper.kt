package io.github.tungnk123.notionsdkkmp.core.data.mapper

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.common.IconDto
import io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionIcon

internal fun IconDto.toDomain(): NotionIcon = when (this) {
    is IconDto.Emoji -> NotionIcon.Emoji(emoji)
    is IconDto.External -> NotionIcon.External(external.url)
    is IconDto.File -> NotionIcon.File(file.url, file.expiryTime)
    is IconDto.CustomEmoji -> NotionIcon.CustomEmoji(
        id = customEmoji.id,
        name = customEmoji.name,
        url = customEmoji.url,
        emoji = customEmoji.emoji
    )
}