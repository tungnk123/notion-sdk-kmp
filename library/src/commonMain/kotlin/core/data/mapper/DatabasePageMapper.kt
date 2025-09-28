package core.data.mapper

import core.data.model.internal.dto.page.PageDto
import core.data.model.result.NotionDatabaseColumn
import core.data.model.result.NotionDatabaseRow

internal fun PageDto.toDomain(): NotionDatabaseRow =
    NotionDatabaseRow(
        id = id,
        icon = iconDto?.toDomain(),
        columns = properties.mapValues { (key, value) -> NotionDatabaseColumn(key, value.toDomain()) }
    )