package repository.user

import core.data.model.result.common.NotionResults
import core.data.model.result.user.NotionUser

interface UserRepository {
    suspend fun list(startCursor: String? = null, pageSize: Int? = null): NotionResults<NotionUser>
    suspend fun retrieve(id: String): NotionUser
    suspend fun me(): NotionUser
}
