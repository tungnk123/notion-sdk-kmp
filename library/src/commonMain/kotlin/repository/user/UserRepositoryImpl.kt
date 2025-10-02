package repository.user

import core.data.mapper.toDomain
import core.data.model.internal.dto.user.UserDto
import core.data.model.result.common.NotionResults
import core.data.model.result.user.NotionUser
import service.user.UserService

class UserRepositoryImpl(private val userService: UserService) : UserRepository {
    override suspend fun list(startCursor: String?, pageSize: Int?): NotionResults<NotionUser> =
        userService.list(startCursor, pageSize).toDomain(UserDto::toDomain)

    override suspend fun retrieve(id: String): NotionUser = userService.retrieve(id).toDomain()
    override suspend fun me(): NotionUser = userService.me().toDomain()
}
