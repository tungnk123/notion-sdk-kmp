package core.data.mapper

import core.data.model.internal.dto.block.ParentDto
import core.data.model.internal.dto.page.PageDto
import core.data.model.result.page.NotionPage

private fun ParentDto.toId(): String = when (this) {
    is ParentDto.DatabaseId -> databaseId
    is ParentDto.DataSourceId -> dataSourceId
    is ParentDto.PageId -> pageId
    is ParentDto.BlockId -> blockId
    is ParentDto.Workspace -> "workspace"
}

fun PageDto.toDomain(): NotionPage = NotionPage(
    id = id,
    createdTime = createdTime,
    lastEditedTime = lastEditedTime,
    createdBy = createdBy.toDomain(),
    lastEditedBy = lastEditedBy.toDomain(),
    cover = cover?.toDomain(),
    icon = icon?.toDomain(),
    parentId = parent.toId(),
    archived = archived,
    inTrash = inTrash,
    properties = properties.mapValues { it.value.toPageDomain() },
    url = url,
    publicUrl = publicUrl
)