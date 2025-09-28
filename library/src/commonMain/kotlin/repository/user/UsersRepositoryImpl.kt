package repository.user

import core.data.mapper.toDomain
import core.data.model.internal.dto.UserDto
import core.data.model.result.NotionResults
import core.data.model.result.NotionUser
import service.user.UsersService

class UsersRepositoryImpl(private val usersService: UsersService) : UsersRepository {
    override suspend fun list(startCursor: String?, pageSize: Int?): NotionResults<NotionUser> =
        usersService.list(startCursor, pageSize).toDomain(UserDto::toDomain)

    override suspend fun retrieve(id: String): NotionUser = usersService.retrieve(id).toDomain()
    override suspend fun me(): NotionUser = usersService.me().toDomain()
}
