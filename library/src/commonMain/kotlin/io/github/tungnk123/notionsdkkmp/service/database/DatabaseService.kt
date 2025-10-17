package io.github.tungnk123.notionsdkkmp.service.database

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.database.DatabaseDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.CreateDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.UpdateDatabaseRequest

interface DatabaseService {
    suspend fun create(request: CreateDatabaseRequest): DatabaseDto
    suspend fun retrieve(id: String): DatabaseDto
    suspend fun update(id: String, request: UpdateDatabaseRequest): DatabaseDto
}
