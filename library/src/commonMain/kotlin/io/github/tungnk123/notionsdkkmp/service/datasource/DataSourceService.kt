package io.github.tungnk123.notionsdkkmp.service.datasource

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourceDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.page.PageDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.datasource.CreateDataSourceRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.datasource.QueryDataSourceRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.datasource.UpdateDataSourceRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.response.ResultsResponseDto

interface DataSourceService {
    suspend fun create(request: CreateDataSourceRequest): DataSourceDto
    suspend fun update(id: String, request: UpdateDataSourceRequest): DataSourceDto
    suspend fun retrieve(id: String): DataSourceDto
    suspend fun query(id: String, request: QueryDataSourceRequest): ResultsResponseDto<PageDto>
}
