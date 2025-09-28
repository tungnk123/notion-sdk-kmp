package core.data.mapper

import core.data.model.internal.response.RetrieveDatabaseResponseDto
import core.data.model.result.NotionDatabaseSchema

internal fun RetrieveDatabaseResponseDto.toDomain(): NotionDatabaseSchema = NotionDatabaseSchema(id = id,
    createdTime = createdTime,
    lastEditedTime = lastEditedTime,
    title = fullTitle(),
    schema = properties.mapValues { (_, value) -> value.toDomain() })