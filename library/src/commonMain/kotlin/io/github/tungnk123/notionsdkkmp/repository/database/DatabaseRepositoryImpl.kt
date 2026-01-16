package io.github.tungnk123.notionsdkkmp.repository.database

import io.github.tungnk123.notionsdkkmp.core.data.mapper.toDomain
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.page.PageDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.CreateDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.QueryDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.UpdateDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionResults
import io.github.tungnk123.notionsdkkmp.core.data.model.result.database.NotionDatabase
import io.github.tungnk123.notionsdkkmp.core.data.model.result.page.NotionPage
import io.github.tungnk123.notionsdkkmp.service.database.DatabaseService

class DatabaseRepositoryImpl(
    private val service: DatabaseService
) : DatabaseRepository {

    override suspend fun create(req: CreateDatabaseRequest): NotionDatabase =
        service.create(req).toDomain()

    override suspend fun retrieve(id: String): NotionDatabase =
        service.retrieve(id).toDomain()

    override suspend fun update(id: String, req: UpdateDatabaseRequest): NotionDatabase =
        service.update(id, req).toDomain()

    override suspend fun query(id: String, req: QueryDatabaseRequest): NotionResults<NotionPage> =
        service.query(id, req).toDomain(PageDto::toDomain)
}
