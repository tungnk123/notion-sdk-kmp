package repository.user

import core.data.model.result.NotionResults
import core.data.model.result.NotionUser

interface UserRepository {
    suspend fun list(startCursor: String? = null, pageSize: Int? = null): NotionResults<NotionUser>
    suspend fun retrieve(id: String): NotionUser
    suspend fun me(): NotionUser
}
