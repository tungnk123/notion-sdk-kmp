package repository.search

import core.data.model.result.common.NotionResults
import core.data.model.result.datasource.NotionDataSource
import core.data.model.result.page.NotionPage

interface SearchRepository {
    suspend fun searchPages(
        query: String? = null, directionAsc: Boolean = false, startCursor: String? = null, pageSize: Int? = null
    ): NotionResults<NotionPage>

    suspend fun searchDataSources(
        query: String? = null, directionAsc: Boolean = false, startCursor: String? = null, pageSize: Int? = null
    ): NotionResults<NotionDataSource>
}