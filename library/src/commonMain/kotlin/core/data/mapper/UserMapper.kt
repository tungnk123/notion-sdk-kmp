package core.data.mapper

import core.data.model.internal.obj.UserObject
import core.data.model.result.NotionUser

fun UserObject.toDomain(): NotionUser {
    return when (this) {
        is UserObject.Person -> NotionUser.Person(
            id = id,
            name = name,
            avatarUrl = avatarUrl,
            email = person.email
        )

        is UserObject.Bot -> {
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