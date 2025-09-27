package core.data.mapper

import core.data.model.internal.response.RetrieveDatabaseResponse
import core.data.model.result.NotionDatabaseSchema

internal fun RetrieveDatabaseResponse.toDomain(): NotionDatabaseSchema = NotionDatabaseSchema(id = id,
    createdTime = createdTime,
    lastEditedTime = lastEditedTime,
    title = fullTitle(),
    schema = properties.mapValues { (_, value) -> value.toDomain() })