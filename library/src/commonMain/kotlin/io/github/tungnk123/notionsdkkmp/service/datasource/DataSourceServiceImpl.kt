package io.github.tungnk123.notionsdkkmp.service.datasource

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourceDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.page.PageDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.datasource.CreateDataSourceRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.datasource.QueryDataSourceRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.datasource.UpdateDataSourceRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.response.ResultsResponseDto
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.request.*

private object Routes {
    private const val DATA_SOURCES = "data_sources"
    fun create() = DATA_SOURCES
    fun update(id: String) = "$DATA_SOURCES/$id"
    fun retrieve(id: String) = "$DATA_SOURCES/$id"
    fun query(id: String) = "$DATA_SOURCES/$id/query"
}

class DataSourceServiceImpl(
    private val http: NotionHttp
) : DataSourceService {

    override suspend fun create(request: CreateDataSourceRequest): DataSourceDto =
        http.post(Routes.create()) { setBody(request) }

    override suspend fun update(id: String, request: UpdateDataSourceRequest): DataSourceDto =
        http.patch(Routes.update(id)) { setBody(request) }

    override suspend fun retrieve(id: String): DataSourceDto = http.get(Routes.retrieve(id))

    override suspend fun query(
        id: String, request: QueryDataSourceRequest
    ): ResultsResponseDto<PageDto> = http.post(Routes.query(id)) { setBody(request) }
}
