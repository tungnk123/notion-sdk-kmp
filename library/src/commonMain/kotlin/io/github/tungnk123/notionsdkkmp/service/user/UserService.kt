package io.github.tungnk123.notionsdkkmp.service.user

import core.data.model.internal.dto.user.UserDto
import core.data.model.internal.response.ResultsResponseDto

interface UserService {
    suspend fun list(startCursor: String?, pageSize: Int?): ResultsResponseDto<UserDto>
    suspend fun retrieve(id: String): UserDto
    suspend fun me(): UserDto
}
