package io.github.tungnk123.notionsdkkmp.service.database

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.database.DatabaseDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.page.PageDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.CreateDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.QueryDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database.UpdateDatabaseRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.response.ResultsResponseDto

interface DatabaseService {
    suspend fun create(request: CreateDatabaseRequest): DatabaseDto
    suspend fun retrieve(id: String): DatabaseDto
    suspend fun update(id: String, request: UpdateDatabaseRequest): DatabaseDto
    suspend fun query(id: String, request: QueryDatabaseRequest): ResultsResponseDto<PageDto>
}
