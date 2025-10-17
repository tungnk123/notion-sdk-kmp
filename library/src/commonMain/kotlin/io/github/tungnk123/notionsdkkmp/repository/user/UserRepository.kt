package io.github.tungnk123.notionsdkkmp.repository.user

import io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionResults
import io.github.tungnk123.notionsdkkmp.core.data.model.result.user.NotionUser

interface UserRepository {
    suspend fun list(startCursor: String? = null, pageSize: Int? = null): NotionResults<NotionUser>
    suspend fun retrieve(id: String): NotionUser
    suspend fun me(): NotionUser
}
