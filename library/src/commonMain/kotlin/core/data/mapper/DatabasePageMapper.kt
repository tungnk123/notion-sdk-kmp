package core.data.mapper

import core.data.model.internal.response.PageObject
import core.data.model.result.NotionDatabaseColumn
import core.data.model.result.NotionDatabaseRow

internal fun PageObject.toDomain(): NotionDatabaseRow =
    NotionDatabaseRow(
        id = id,
        icon = iconObject?.toDomain(),
        columns = properties.mapValues { (key, value) -> NotionDatabaseColumn(key, value.toDomain()) }
    )