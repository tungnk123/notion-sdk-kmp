package core.data.mapper

import core.data.model.internal.dto.datasource.*
import core.data.model.result.common.NotionCover
import core.data.model.result.common.NotionParent
import core.data.model.result.common.NotionPartialUser
import core.data.model.result.datasource.NotionDataSource
import core.data.model.result.datasource.NotionDataSourceProperty
import core.data.model.result.datasource.NotionDataSourceProperty.*

fun DataSourceDto.toDomain(): NotionDataSource = NotionDataSource(
    id = id,
    properties = properties.mapValues { it.value.toDomain() },
    parent = parent?.toDomain(),
    databaseParent = databaseParent?.toDomain(),
    createdTime = createdTime,
    createdBy = createdBy?.toDomain(),
    lastEditedTime = lastEditedTime,
    lastEditedBy = lastEditedBy?.toDomain(),
    title = (title ?: emptyList()).map { it.toDomain() },
    description = (description ?: emptyList()).map { it.toDomain() },
    icon = icon?.toDomain(),
    cover = cover?.toDomain(),
    archived = archived ?: false,
    isInline = isInline ?: false,
    url = url,
    inTrash = inTrash ?: false
)

fun DataSourcePropertyDto.toDomain(): NotionDataSourceProperty = when (type) {
    DataSourcePropertyType.TITLE -> Title(id, name, description)

    DataSourcePropertyType.RICH_TEXT -> RichText(id, name, description)

    DataSourcePropertyType.CHECKBOX -> Checkbox(id, name, description)

    DataSourcePropertyType.DATE -> Date(id, name, description)

    DataSourcePropertyType.EMAIL -> Email(id, name, description)

    DataSourcePropertyType.FILES -> Files(id, name, description)

    DataSourcePropertyType.PEOPLE -> People(id, name, description)

    DataSourcePropertyType.URL -> Url(id, name, description)

    DataSourcePropertyType.PHONE_NUMBER -> PhoneNumber(id, name, description)

    DataSourcePropertyType.NUMBER -> Number(id, name, description, number?.format)

    DataSourcePropertyType.SELECT -> Select(
        id, name, description, select?.options.orEmpty().map { Option(it.id, it.name, it.color) })

    DataSourcePropertyType.MULTI_SELECT -> MultiSelect(
        id, name, description, multiSelect?.options.orEmpty().map { Option(it.id, it.name, it.color) })

    DataSourcePropertyType.STATUS -> Status(
        id,
        name,
        description,
        status?.options.orEmpty().map { Option(it.id, it.name, it.color) },
        status?.groups.orEmpty().map {
            NotionDataSourceProperty.StatusGroup(
                id = it.id, name = it.name, color = it.color, optionIds = it.optionIds
            )
        })

    DataSourcePropertyType.RELATION -> Relation(
        id = id,
        name = name,
        description = description,
        targetDataSourceId = relation?.dataSourceId ?: relation?.databaseIdLegacy,
        syncedPropertyId = relation?.syncedPropertyId,
        syncedPropertyName = relation?.syncedPropertyName
    )

    DataSourcePropertyType.ROLLUP -> Rollup(
        id = id,
        name = name,
        description = description,
        function = rollup?.function,
        relationPropertyId = rollup?.relationPropertyId,
        relationPropertyName = rollup?.relationPropertyName,
        rollupPropertyId = rollup?.rollupPropertyId,
        rollupPropertyName = rollup?.rollupPropertyName
    )

    DataSourcePropertyType.CREATED_BY -> CreatedBy(id, name, description)

    DataSourcePropertyType.CREATED_TIME -> CreatedTime(id, name, description)

    DataSourcePropertyType.LAST_EDITED_BY -> LastEditedBy(id, name, description)

    DataSourcePropertyType.LAST_EDITED_TIME -> LastEditedTime(id, name, description)

    DataSourcePropertyType.UNIQUE_ID -> UniqueId(id, name, description, uniqueId?.prefix)

    DataSourcePropertyType.FORMULA -> TODO()
}

fun ParentDto.toDomain(): NotionParent = when (this) {
    is ParentDto.DatabaseId   -> NotionParent.DatabaseId(databaseId)
    is ParentDto.DataSourceId -> NotionParent.DataSourceId(dataSourceId)
    is ParentDto.PageId       -> NotionParent.PageId(pageId)
    is ParentDto.BlockId      -> NotionParent.BlockId(blockId)
    is ParentDto.Workspace    -> NotionParent.Workspace(workspace)
}

fun PartialUser.toDomain(): NotionPartialUser = NotionPartialUser(id)

fun CoverDto.toDomain(): NotionCover = when (this) {
    is CoverDto.File     -> NotionCover.File(file.url, file.expiryTime)
    is CoverDto.External -> NotionCover.External(external.url)
}
