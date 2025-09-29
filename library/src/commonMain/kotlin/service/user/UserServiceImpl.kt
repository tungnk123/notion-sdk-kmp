package service.user

import core.data.model.internal.dto.UserDto
import core.data.model.internal.response.ResultsResponseDto
import http.NotionHttp
import io.ktor.client.request.*

class UserServiceImpl(private val http: NotionHttp) : UserService {
    override suspend fun list(startCursor: String?, pageSize: Int?): ResultsResponseDto<UserDto> = http.get("users") {
        if (startCursor != null) parameter("start_cursor", startCursor)
        if (pageSize != null) parameter("page_size", pageSize)
    }

    override suspend fun retrieve(id: String): UserDto = http.get("users/$id")

    override suspend fun me(): UserDto = http.get("users/me")
}
