package service.datasource

import core.data.model.internal.dto.datasource.DataSourceDto
import core.data.model.internal.dto.page.PageDto
import core.data.model.internal.request.datasource.CreateDataSourceRequest
import core.data.model.internal.request.datasource.QueryDataSourceRequest
import core.data.model.internal.request.datasource.UpdateDataSourceRequest
import core.data.model.internal.response.ResultsResponseDto

interface DataSourcesService {
    suspend fun create(request: CreateDataSourceRequest): DataSourceDto
    suspend fun update(id: String, request: UpdateDataSourceRequest): DataSourceDto
    suspend fun retrieve(id: String): DataSourceDto
    suspend fun query(id: String, request: QueryDataSourceRequest): ResultsResponseDto<PageDto>
}
