package service.user

import core.data.model.internal.dto.user.UserDto
import core.data.model.internal.response.ResultsResponseDto
import io.github.tungnk123.notionsdkkmp.http.NotionHttp

private object Routes {
    private const val USERS = "users"
    fun list() = USERS
    fun retrieve(id: String) = "$USERS/$id"
    fun me() = "$USERS/me"
}

class UserServiceImpl(private val http: NotionHttp) : UserService {
    override suspend fun list(startCursor: String?, pageSize: Int?): ResultsResponseDto<UserDto> =
        http.get(Routes.list()) {
            if (startCursor != null) url.parameters.append("start_cursor", startCursor)
            if (pageSize != null) url.parameters.append("page_size", pageSize.toString())
        }

    override suspend fun retrieve(id: String): UserDto = http.get(Routes.retrieve(id))

    override suspend fun me(): UserDto = http.get(Routes.me())
}