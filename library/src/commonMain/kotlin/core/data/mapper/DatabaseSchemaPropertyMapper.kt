package core.data.mapper

import core.data.model.internal.response.RetrieveDatabaseSchemaPropertyDto
import core.data.model.result.database.NotionDatabasePropertySchema

internal fun RetrieveDatabaseSchemaPropertyDto.toDomain(): NotionDatabasePropertySchema = when (this) {
    is RetrieveDatabaseSchemaPropertyDto.Title -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.Text -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.Number -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.Select -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.MultiSelect -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.Formula -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.Date -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.People -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.Files -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.Checkbox -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.Url -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.Email -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.PhoneNumber -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.CreatedTime -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.CreatedBy -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.LastEditedTime -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.LastEditedBy -> toDomain()
    is RetrieveDatabaseSchemaPropertyDto.Rollup -> toDomain()
}

internal fun RetrieveDatabaseSchemaPropertyDto.Title.toDomain(): NotionDatabasePropertySchema.Title =
    NotionDatabasePropertySchema.Title(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.Text.toDomain(): NotionDatabasePropertySchema.Text =
    NotionDatabasePropertySchema.Text(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.Number.toDomain(): NotionDatabasePropertySchema.Number =
    NotionDatabasePropertySchema.Number(id = id, name = name, format = number.format.toDomain())

internal fun RetrieveDatabaseSchemaPropertyDto.Select.toDomain(): NotionDatabasePropertySchema.Select =
    NotionDatabasePropertySchema.Select(id = id, name = name, options = select.options.map { it.toDomain() })

internal fun RetrieveDatabaseSchemaPropertyDto.Select.Option.toDomain(): NotionDatabasePropertySchema.Select.Option =
    NotionDatabasePropertySchema.Select.Option(id, name)

internal fun RetrieveDatabaseSchemaPropertyDto.MultiSelect.toDomain(): NotionDatabasePropertySchema.MultiSelect =
    NotionDatabasePropertySchema.MultiSelect(id = id, name = name, options = multiSelect.options.map { it.toDomain() })

internal fun RetrieveDatabaseSchemaPropertyDto.Formula.toDomain(): NotionDatabasePropertySchema.Formula =
    NotionDatabasePropertySchema.Formula(id = id, name = name, expression = formula.expression)

internal fun RetrieveDatabaseSchemaPropertyDto.Date.toDomain(): NotionDatabasePropertySchema.Date =
    NotionDatabasePropertySchema.Date(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.People.toDomain(): NotionDatabasePropertySchema.People =
    NotionDatabasePropertySchema.People(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.Files.toDomain(): NotionDatabasePropertySchema.Files =
    NotionDatabasePropertySchema.Files(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.Checkbox.toDomain(): NotionDatabasePropertySchema.Checkbox =
    NotionDatabasePropertySchema.Checkbox(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.Url.toDomain(): NotionDatabasePropertySchema.Url =
    NotionDatabasePropertySchema.Url(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.Email.toDomain(): NotionDatabasePropertySchema.Email =
    NotionDatabasePropertySchema.Email(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.PhoneNumber.toDomain(): NotionDatabasePropertySchema.PhoneNumber =
    NotionDatabasePropertySchema.PhoneNumber(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.CreatedTime.toDomain(): NotionDatabasePropertySchema.CreatedTime =
    NotionDatabasePropertySchema.CreatedTime(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.CreatedBy.toDomain(): NotionDatabasePropertySchema.CreatedBy =
    NotionDatabasePropertySchema.CreatedBy(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.LastEditedTime.toDomain(): NotionDatabasePropertySchema.LastEditedTime =
    NotionDatabasePropertySchema.LastEditedTime(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.LastEditedBy.toDomain(): NotionDatabasePropertySchema.LastEditedBy =
    NotionDatabasePropertySchema.LastEditedBy(id = id, name = name)

internal fun RetrieveDatabaseSchemaPropertyDto.Rollup.toDomain(): NotionDatabasePropertySchema.Rollup =
    NotionDatabasePropertySchema.Rollup(id = id, name = name)