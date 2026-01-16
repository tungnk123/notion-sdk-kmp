package io.github.tungnk123.notionsdkkmp.repository.database

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.CreateDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.QueryDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.UpdateDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionResults
import io.github.tungnk123.notionsdkkmp.core.data.model.result.database.NotionDatabase
import io.github.tungnk123.notionsdkkmp.core.data.model.result.page.NotionPage

interface DatabaseRepository {
    suspend fun create(req: CreateDatabaseRequest): NotionDatabase
    suspend fun retrieve(id: String): NotionDatabase
    suspend fun update(id: String, req: UpdateDatabaseRequest): NotionDatabase
    suspend fun query(id: String, req: QueryDatabaseRequest): NotionResults<NotionPage>
}
