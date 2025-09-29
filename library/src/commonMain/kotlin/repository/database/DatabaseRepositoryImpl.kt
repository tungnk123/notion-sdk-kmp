package repository.database

import core.data.mapper.toDomain
import core.data.model.internal.request.database.CreateDatabaseRequest
import core.data.model.internal.request.database.UpdateDatabaseRequest
import core.data.model.result.database.NotionDatabase
import service.database.DatabaseService

class DatabaseRepositoryImpl(
    private val service: DatabaseService
) : DatabaseRepository {

    override suspend fun create(req: CreateDatabaseRequest): NotionDatabase =
        service.create(req).toDomain()

    override suspend fun retrieve(id: String): NotionDatabase =
        service.retrieve(id).toDomain()

    override suspend fun update(id: String, req: UpdateDatabaseRequest): NotionDatabase =
        service.update(id, req).toDomain()
}
