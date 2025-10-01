package core.data.mapper

import core.data.model.internal.dto.PartialUserDto
import core.data.model.internal.dto.page.PagePropertyDto
import core.data.model.result.common.NotionPartialUser
import core.data.model.result.page.NotionPageProperty

internal fun PagePropertyDto.toPageDomain(): NotionPageProperty =
    when (this) {
        is PagePropertyDto.Title -> toPageDomain()
        is PagePropertyDto.Text -> toPageDomain()
        is PagePropertyDto.Number -> toPageDomain()
        is PagePropertyDto.Select -> toPageDomain()
        is PagePropertyDto.MultiSelect -> toPageDomain()
        is PagePropertyDto.Date -> toPageDomain()
        is PagePropertyDto.People -> toPageDomain()
        is PagePropertyDto.Files -> toPageDomain()
        is PagePropertyDto.Checkbox -> toPageDomain()
        is PagePropertyDto.Url -> toPageDomain()
        is PagePropertyDto.Email -> toPageDomain()
        is PagePropertyDto.PhoneNumber -> toPageDomain()
        is PagePropertyDto.Formula -> toPageDomain()
        is PagePropertyDto.CreatedTime -> toPageDomain()
        is PagePropertyDto.LastEditedTime -> toPageDomain()
        is PagePropertyDto.CreatedBy -> toPageDomain()
        is PagePropertyDto.LastEditedBy -> toPageDomain()
        is PagePropertyDto.Rollup -> toPageDomain()
        is PagePropertyDto.Status -> toPageDomain()
        is PagePropertyDto.Relation -> toPageDomain()
        is PagePropertyDto.UniqueId -> toPageDomain()
        is PagePropertyDto.Verification -> toPageDomain()
    }

internal fun PagePropertyDto.Title.toPageDomain(): NotionPageProperty.Title =
    NotionPageProperty.Title(
        id = id,
        title = title.map {
            NotionPageProperty.Title.Value(
                type = it.type,
                text = NotionPageProperty.Title.Value.Text(
                    content = it.text.content,
                    link = it.text.link
                ),
                annotations = NotionPageProperty.Title.Value.Annotations(
                    bold = it.annotations.bold,
                    italic = it.annotations.italic,
                    strikethrough = it.annotations.strikethrough,
                    underline = it.annotations.underline,
                    code = it.annotations.code,
                    color = it.annotations.color
                ),
                plainText = it.plainText,
                href = it.href
            )
        }
    )

internal fun PagePropertyDto.Text.toPageDomain(): NotionPageProperty.Text =
    NotionPageProperty.Text(
        id = id,
        richText = richText.map { NotionPageProperty.Text.Value(it.plainText, it.href) }
    )

internal fun PagePropertyDto.Number.toPageDomain(): NotionPageProperty.Number =
    NotionPageProperty.Number(id = id, number = number)

internal fun PagePropertyDto.Select.toPageDomain(): NotionPageProperty.Select =
    NotionPageProperty.Select(
        id = id,
        select = select?.let { NotionPageProperty.Select.Value(it.id, it.name, it.color) }
    )

internal fun PagePropertyDto.MultiSelect.toPageDomain(): NotionPageProperty.MultiSelect =
    NotionPageProperty.MultiSelect(
        id = id,
        multiSelect = multiSelect.map { NotionPageProperty.Select.Value(it.id, it.name, it.color) }
    )

internal fun PagePropertyDto.Date.toPageDomain(): NotionPageProperty.Date =
    NotionPageProperty.Date(
        id = id,
        date = date?.let { NotionPageProperty.Date.Value(it.start, it.end) }
    )

internal fun PagePropertyDto.People.toPageDomain(): NotionPageProperty.People =
    NotionPageProperty.People(
        id = id,
        people = people.map { it.toPageDomain() }
    )

internal fun PagePropertyDto.People.Value.toPageDomain(): NotionPageProperty.People.Value =
    when (this) {
        is PagePropertyDto.People.Value.Person ->
            NotionPageProperty.People.Value.Person(
                id = id,
                name = name,
                avatarUrl = avatarUrl,
                person = NotionPageProperty.People.Value.Person.User(person.email)
            )

        is PagePropertyDto.People.Value.Bot ->
            NotionPageProperty.People.Value.Bot(
                id = id,
                name = name,
                avatarUrl = avatarUrl
            )
    }

internal fun PagePropertyDto.Files.toPageDomain(): NotionPageProperty.Files =
    NotionPageProperty.Files(
        id = id,
        files = files.map { value ->
            when (value) {
                is PagePropertyDto.Files.Value.External ->
                    NotionPageProperty.Files.Value.External(url = value.url)

                is PagePropertyDto.Files.Value.File ->
                    NotionPageProperty.Files.Value.File(
                        name = value.name,
                        file = NotionPageProperty.Files.Value.File.Item(
                            url = value.file.url,
                            expiryTime = value.file.expiryTime
                        )
                    )
            }
        }
    )

internal fun PagePropertyDto.Checkbox.toPageDomain(): NotionPageProperty.Checkbox =
    NotionPageProperty.Checkbox(id = id, checkbox = checkbox)

internal fun PagePropertyDto.Url.toPageDomain(): NotionPageProperty.Url =
    NotionPageProperty.Url(id = id, url = url)

internal fun PagePropertyDto.Email.toPageDomain(): NotionPageProperty.Email =
    NotionPageProperty.Email(id = id, email = email)

internal fun PagePropertyDto.PhoneNumber.toPageDomain(): NotionPageProperty.PhoneNumber =
    NotionPageProperty.PhoneNumber(id = id, phoneNumber = phoneNumber)

internal fun PagePropertyDto.Formula.toPageDomain(): NotionPageProperty.Formula =
    NotionPageProperty.Formula(
        id = id,
        formula = when (val f = formula) {
            is PagePropertyDto.Formula.Value.Str -> NotionPageProperty.Formula.Value.Str(f.string)
            is PagePropertyDto.Formula.Value.Number -> NotionPageProperty.Formula.Value.Number(f.number)
            is PagePropertyDto.Formula.Value.Bool -> NotionPageProperty.Formula.Value.Bool(f.boolean)
            is PagePropertyDto.Formula.Value.Date -> NotionPageProperty.Formula.Value.Date(f.date)
        }
    )

internal fun PagePropertyDto.CreatedTime.toPageDomain(): NotionPageProperty.CreatedTime =
    NotionPageProperty.CreatedTime(id = id, createdTime = createdTime)

internal fun PagePropertyDto.LastEditedTime.toPageDomain(): NotionPageProperty.LastEditedTime =
    NotionPageProperty.LastEditedTime(id = id, lastEditedTime = lastEditedTime)

internal fun PagePropertyDto.CreatedBy.toPageDomain(): NotionPageProperty.CreatedBy =
    NotionPageProperty.CreatedBy(
        id = id,
        createdBy = NotionPageProperty.People.Value.Person(
            id = createdBy.id,
            name = createdBy.name,
            avatarUrl = createdBy.avatarUrl,
            person = NotionPageProperty.People.Value.Person.User(createdBy.person.email)
        )
    )

internal fun PagePropertyDto.LastEditedBy.toPageDomain(): NotionPageProperty.LastEditedBy =
    NotionPageProperty.LastEditedBy(
        id = id,
        lastEditedBy = NotionPageProperty.People.Value.Person(
            id = lastEditedBy.id,
            name = lastEditedBy.name,
            avatarUrl = lastEditedBy.avatarUrl,
            person = NotionPageProperty.People.Value.Person.User(lastEditedBy.person.email)
        )
    )

internal fun PagePropertyDto.Rollup.toPageDomain(): NotionPageProperty.Rollup =
    NotionPageProperty.Rollup(id = id)

internal fun PagePropertyDto.Status.toPageDomain(): NotionPageProperty.Status =
    NotionPageProperty.Status(
        id = id,
        status = NotionPageProperty.StatusValue(
            id = status.id,
            name = status.name,
            color = status.color
        )
    )

internal fun PagePropertyDto.Relation.toPageDomain(): NotionPageProperty.Relation =
    NotionPageProperty.Relation(
        id = id,
        relation = relation.map { NotionPageProperty.Relation.Ref(it.id) },
        hasMore = hasMore
    )

internal fun PagePropertyDto.UniqueId.toPageDomain(): NotionPageProperty.UniqueId =
    NotionPageProperty.UniqueId(
        id = id,
        uniqueId = NotionPageProperty.UniqueId.Value(
            number = uniqueId.number,
            prefix = uniqueId.prefix
        )
    )

internal fun PagePropertyDto.Verification.toPageDomain(): NotionPageProperty.Verification =
    NotionPageProperty.Verification(
        id = id,
        verification = NotionPageProperty.Verification.Value(
            state = verification.state,
            verifiedBy = verification.verifiedBy?.let {
                NotionPageProperty.Verification.VerifiedBy(
                    objectType = it.objectType,
                    id = it.id,
                    name = it.name,
                    avatarUrl = it.avatarUrl,
                    type = it.type
                )
            },
            date = verification.date?.let {
                NotionPageProperty.Verification.DateRange(
                    start = it.start,
                    end = it.end,
                    timeZone = it.timeZone
                )
            }
        )
    )

internal fun PartialUserDto.toDomain(): NotionPartialUser = NotionPartialUser(
    id = id
)