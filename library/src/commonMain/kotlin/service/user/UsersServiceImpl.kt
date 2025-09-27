package service.user

import core.data.model.internal.obj.UserObject
import core.data.model.internal.response.ResultsResponse
import http.NotionHttp
import io.ktor.client.request.*

class UsersServiceImpl(private val http: NotionHttp) : UsersService {
    override suspend fun list(startCursor: String?, pageSize: Int?): ResultsResponse<UserObject> = http.get("users") {
        if (startCursor != null) parameter("start_cursor", startCursor)
        if (pageSize != null) parameter("page_size", pageSize)
    }

    override suspend fun retrieve(id: String): UserObject = http.get("users/$id")

    override suspend fun me(): UserObject = http.get("users/me")
}
