package io.github.tungnk123.notionsdkkmp.core.data.mapper

import io.github.tungnk123.notionsdkkmp.core.data.mapper.toDomain
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.response.RetrieveDatabaseResponseDto
import io.github.tungnk123.notionsdkkmp.core.data.model.result.database.NotionDatabaseSchema

internal fun RetrieveDatabaseResponseDto.toDomain(): NotionDatabaseSchema =
    NotionDatabaseSchema(
        id = id,
        createdTime = createdTime,
        lastEditedTime = lastEditedTime,
        title = fullTitle(),
        schema = properties.mapValues { (_, value) -> value.toDomain() })