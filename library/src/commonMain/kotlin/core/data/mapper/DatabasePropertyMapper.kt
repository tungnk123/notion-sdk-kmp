package core.data.mapper

import core.data.model.internal.dto.page.PagePropertyDto
import core.data.model.result.NotionDatabaseProperty

internal fun PagePropertyDto.toDomain(): NotionDatabaseProperty =
    when (this) {
        is PagePropertyDto.Title -> toDomain()
        is PagePropertyDto.Text -> toDomain()
        is PagePropertyDto.Number -> toDomain()
        is PagePropertyDto.Select -> toDomain()
        is PagePropertyDto.MultiSelect -> toDomain()
        is PagePropertyDto.Date -> toDomain()
        is PagePropertyDto.People -> toDomain()
        is PagePropertyDto.Files -> toDomain()
        is PagePropertyDto.Checkbox -> toDomain()
        is PagePropertyDto.Url -> toDomain()
        is PagePropertyDto.Email -> toDomain()
        is PagePropertyDto.PhoneNumber -> toDomain()
        is PagePropertyDto.Formula -> toDomain()
        is PagePropertyDto.Relation -> toDomain()
        is PagePropertyDto.CreatedTime -> toDomain()
        is PagePropertyDto.LastEditedTime -> toDomain()
        is PagePropertyDto.CreatedBy -> toDomain()
        is PagePropertyDto.LastEditedBy -> toDomain()
        is PagePropertyDto.Rollup -> toDomain()
    }

internal fun PagePropertyDto.Title.toDomain(): NotionDatabaseProperty.Title =
    NotionDatabaseProperty.Title(
        id = id,
        text = plainText()
    )

internal fun PagePropertyDto.Text.toDomain(): NotionDatabaseProperty.Text =
    NotionDatabaseProperty.Text(
        id = id,
        text = plainText(),
        parts = richText.map { NotionDatabaseProperty.Text.Part(it.plainText, it.href) }
    )

internal fun PagePropertyDto.Number.toDomain(): NotionDatabaseProperty.Number =
    NotionDatabaseProperty.Number(
        id = id,
        number = number
    )

internal fun PagePropertyDto.Select.toDomain(): NotionDatabaseProperty.Select =
    NotionDatabaseProperty.Select(
        id = id,
        selected = select?.toDomain()
    )

internal fun PagePropertyDto.Select.Value.toDomain(): NotionDatabaseProperty.Select.Option =
    NotionDatabaseProperty.Select.Option(
        id = id,
        name = name
    )

internal fun PagePropertyDto.MultiSelect.toDomain(): NotionDatabaseProperty.MultiSelect =
    NotionDatabaseProperty.MultiSelect(
        id = id,
        selected = multiSelect.map(PagePropertyDto.Select.Value::toDomain)
    )

internal fun PagePropertyDto.Date.toDomain(): NotionDatabaseProperty.Date =
    NotionDatabaseProperty.Date(
        id = id,
        start = date?.start,
        end = date?.end
    )

internal fun PagePropertyDto.People.toDomain(): NotionDatabaseProperty.People =
    NotionDatabaseProperty.People(
        id = id,
        people = people.map { value -> value.toDomain() }
    )

internal fun PagePropertyDto.People.Value.toDomain(): NotionDatabaseProperty.People.Person =
    when (this) {
        is PagePropertyDto.People.Value.Person ->
            NotionDatabaseProperty.People.Person.User(
                id = id,
                name = name,
                avatarUrl = avatarUrl,
                email = person.email
            )

        is PagePropertyDto.People.Value.Bot ->
            NotionDatabaseProperty.People.Person.Bot(
                id = id,
                name = name,
                avatarUrl = avatarUrl
            )
    }

internal fun PagePropertyDto.Files.toDomain(): NotionDatabaseProperty.Files =
    NotionDatabaseProperty.Files(
        id = id,
        files = files.map { value ->
            when (value) {
                is PagePropertyDto.Files.Value.External ->
                    NotionDatabaseProperty.Files.Item(
                        url = value.url,
                        name = null,
                        expiryTime = null,
                        type = NotionDatabaseProperty.Files.Item.Type.External
                    )

                is PagePropertyDto.Files.Value.File ->
                    NotionDatabaseProperty.Files.Item(
                        url = value.file.url,
                        name = value.name,
                        expiryTime = value.file.expiryTime,
                        type = NotionDatabaseProperty.Files.Item.Type.File
                    )
            }
        }
    )

internal fun PagePropertyDto.Checkbox.toDomain(): NotionDatabaseProperty.Checkbox =
    NotionDatabaseProperty.Checkbox(
        id = id,
        selected = checkbox
    )

internal fun PagePropertyDto.Url.toDomain(): NotionDatabaseProperty.Url =
    NotionDatabaseProperty.Url(
        id = id,
        url = url
    )

internal fun PagePropertyDto.Email.toDomain(): NotionDatabaseProperty.Email =
    NotionDatabaseProperty.Email(
        id = id,
        email = email
    )

internal fun PagePropertyDto.PhoneNumber.toDomain(): NotionDatabaseProperty.PhoneNumber =
    NotionDatabaseProperty.PhoneNumber(
        id = id,
        phoneNumber = phoneNumber
    )

internal fun PagePropertyDto.Formula.toDomain(): NotionDatabaseProperty.Formula =
    NotionDatabaseProperty.Formula(
        id = id,
        formula = when (formula) {
            is PagePropertyDto.Formula.Value.Str -> NotionDatabaseProperty.Formula.Item.Str(formula.string)
            is PagePropertyDto.Formula.Value.Number -> NotionDatabaseProperty.Formula.Item.Number(formula.number)
            is PagePropertyDto.Formula.Value.Bool -> NotionDatabaseProperty.Formula.Item.Bool(formula.boolean)
            is PagePropertyDto.Formula.Value.Date -> NotionDatabaseProperty.Formula.Item.Date(formula.date)
        }
    )

internal fun PagePropertyDto.Relation.toDomain(): NotionDatabaseProperty.Relation =
    NotionDatabaseProperty.Relation(
        id = id
    )

internal fun PagePropertyDto.CreatedBy.toDomain(): NotionDatabaseProperty.CreatedBy =
    NotionDatabaseProperty.CreatedBy(
        id = id,
        createdBy = createdBy.toDomain()
    )

internal fun PagePropertyDto.LastEditedBy.toDomain(): NotionDatabaseProperty.LastEditedBy =
    NotionDatabaseProperty.LastEditedBy(
        id = id,
        lastEditedBy = lastEditedBy.toDomain()
    )

internal fun PagePropertyDto.CreatedTime.toDomain(): NotionDatabaseProperty.CreatedTime =
    NotionDatabaseProperty.CreatedTime(
        id = id,
        createdTime = createdTime
    )

internal fun PagePropertyDto.LastEditedTime.toDomain(): NotionDatabaseProperty.LastEditedTime =
    NotionDatabaseProperty.LastEditedTime(
        id = id,
        lastEditedTime = lastEditedTime
    )

internal fun PagePropertyDto.Rollup.toDomain(): NotionDatabaseProperty.Rollup =
    NotionDatabaseProperty.Rollup(
        id = id,
    )