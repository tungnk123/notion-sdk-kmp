package repository.datasource

import core.data.mapper.toDomain
import core.data.model.internal.dto.page.PageDto
import core.data.model.internal.request.datasource.CreateDataSourceRequest
import core.data.model.internal.request.datasource.QueryDataSourceRequest
import core.data.model.internal.request.datasource.UpdateDataSourceRequest
import core.data.model.result.NotionDatabaseRow
import core.data.model.result.NotionResults
import core.data.model.result.datasource.NotionDataSource
import service.datasource.DataSourcesService

class DataSourcesRepositoryImpl(
    private val service: DataSourcesService
) : DataSourcesRepository {

    override suspend fun create(req: CreateDataSourceRequest): NotionDataSource = service.create(req).toDomain()

    override suspend fun update(id: String, req: UpdateDataSourceRequest): NotionDataSource =
        service.update(id, req).toDomain()

    override suspend fun retrieve(id: String): NotionDataSource = service.retrieve(id).toDomain()

    override suspend fun query(
        id: String, req: QueryDataSourceRequest
    ): NotionResults<NotionDatabaseRow> = service.query(id, req).toDomain(PageDto::toDomain)
}
