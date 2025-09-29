package core.data.mapper

import core.data.model.internal.dto.database.ChildDataSourceRef
import core.data.model.internal.dto.database.DatabaseDto
import core.data.model.result.database.ChildDataSource
import core.data.model.result.database.NotionDatabase

fun DatabaseDto.toDomain(): NotionDatabase = NotionDatabase(
    id = id,
    dataSources = dataSources.map { it.toDomain() },
    createdTime = createdTime,
    createdBy = createdBy?.toDomain(),
    lastEditedTime = lastEditedTime,
    lastEditedBy = lastEditedBy?.toDomain(),
    title = (title ?: emptyList()).map { it.toDomain() },
    description = (description ?: emptyList()).map { it.toDomain() },
    icon = icon?.toDomain(),
    cover = cover?.toDomain(),
    parent = parent?.toDomain(),
    url = url,
    archived = archived ?: false,
    inTrash = inTrash ?: false,
    isInline = isInline ?: false,
    publicUrl = publicUrl
)

fun ChildDataSourceRef.toDomain(): ChildDataSource = ChildDataSource(id = id, name = name)
