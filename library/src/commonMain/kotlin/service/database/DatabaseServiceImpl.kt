package service.database

import core.data.model.internal.dto.database.DatabaseDto
import core.data.model.internal.request.database.CreateDatabaseRequest
import core.data.model.internal.request.database.UpdateDatabaseRequest
import http.NotionHttp
import io.ktor.client.request.*

private object Routes {
    private const val DATABASES = "databases"
    fun create() = DATABASES
    fun retrieve(id: String) = "$DATABASES/$id"
    fun update(id: String) = "$DATABASES/$id"
}

class DatabaseServiceImpl(
    private val http: NotionHttp
) : DatabaseService {

    override suspend fun create(request: CreateDatabaseRequest): DatabaseDto =
        http.post(Routes.create()) { setBody(request) }

    override suspend fun retrieve(id: String): DatabaseDto =
        http.get(Routes.retrieve(id))

    override suspend fun update(id: String, request: UpdateDatabaseRequest): DatabaseDto =
        http.patch(Routes.update(id)) { setBody(request) }
}
