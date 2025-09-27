package service.user

import core.data.model.internal.obj.UserObject
import core.data.model.internal.response.ResultsResponse

interface UsersService {
    suspend fun list(startCursor: String?, pageSize: Int?): ResultsResponse<UserObject>
    suspend fun retrieve(id: String): UserObject
    suspend fun me(): UserObject
}
