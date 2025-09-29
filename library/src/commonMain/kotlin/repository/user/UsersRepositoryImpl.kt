package repository.user

import core.data.mapper.toDomain
import core.data.model.internal.dto.UserDto
import core.data.model.result.NotionResults
import core.data.model.result.NotionUser
import service.user.UserService

class UsersRepositoryImpl(private val userService: UserService) : UsersRepository {
    override suspend fun list(startCursor: String?, pageSize: Int?): NotionResults<NotionUser> =
        userService.list(startCursor, pageSize).toDomain(UserDto::toDomain)

    override suspend fun retrieve(id: String): NotionUser = userService.retrieve(id).toDomain()
    override suspend fun me(): NotionUser = userService.me().toDomain()
}
