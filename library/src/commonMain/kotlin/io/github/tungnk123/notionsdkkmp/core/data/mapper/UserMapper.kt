package io.github.tungnk123.notionsdkkmp.core.data.mapper

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.user.UserDto
import io.github.tungnk123.notionsdkkmp.core.data.model.result.user.NotionUser

fun UserDto.toDomain(): NotionUser {
    return when (this) {
        is UserDto.Person -> NotionUser.Person(
            id = id,
            name = name,
            avatarUrl = avatarUrl,
            email = person.email
        )

        is UserDto.Bot -> {
            val owner = when (bot?.owner?.type) {
                "workspace" -> NotionUser.Bot.Owner.Workspace(bot.owner.workspace == true)
                "user" -> {
                    val uid = bot.owner.user?.id ?: return NotionUser.Bot(
                        id = id,
                        name = name,
                        avatarUrl = avatarUrl,
                        owner = null,
                        workspaceName = bot.workspaceName,
                        workspaceLimits = bot.workspaceLimits?.let {
                            NotionUser.Bot.WorkspaceLimits(it.maxFileUploadSizeInBytes)
                        }
                    )
                    NotionUser.Bot.Owner.User(uid)
                }

                else -> null
            }
            NotionUser.Bot(
                id = id,
                name = name,
                avatarUrl = avatarUrl,
                owner = owner,
                workspaceName = bot?.workspaceName,
                workspaceLimits = bot?.workspaceLimits?.let {
                    NotionUser.Bot.WorkspaceLimits(it.maxFileUploadSizeInBytes)
                }
            )
        }
    }
}