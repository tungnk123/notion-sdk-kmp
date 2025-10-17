package io.github.tungnk123.notionsdkkmp.repository.database

import core.data.model.internal.request.database.CreateDatabaseRequest
import core.data.model.internal.request.database.UpdateDatabaseRequest
import core.data.model.result.database.NotionDatabase

interface DatabaseRepository {
    suspend fun create(req: CreateDatabaseRequest): NotionDatabase
    suspend fun retrieve(id: String): NotionDatabase
    suspend fun update(id: String, req: UpdateDatabaseRequest): NotionDatabase
}
