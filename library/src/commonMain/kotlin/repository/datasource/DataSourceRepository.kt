package repository.datasource

import core.data.model.internal.request.datasource.CreateDataSourceRequest
import core.data.model.internal.request.datasource.QueryDataSourceRequest
import core.data.model.internal.request.datasource.UpdateDataSourceRequest
import core.data.model.result.NotionResults
import core.data.model.result.datasource.NotionDataSource
import core.data.model.result.page.NotionPage

interface DataSourceRepository {
    suspend fun create(req: CreateDataSourceRequest): NotionDataSource
    suspend fun update(id: String, req: UpdateDataSourceRequest): NotionDataSource
    suspend fun retrieve(id: String): NotionDataSource
    suspend fun query(id: String, req: QueryDataSourceRequest): NotionResults<NotionPage>
}
