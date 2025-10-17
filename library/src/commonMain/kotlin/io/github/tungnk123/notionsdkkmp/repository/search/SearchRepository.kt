package io.github.tungnk123.notionsdkkmp.repository.search

import io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionResults
import io.github.tungnk123.notionsdkkmp.core.data.model.result.datasource.NotionDataSource
import io.github.tungnk123.notionsdkkmp.core.data.model.result.page.NotionPage

interface SearchRepository {
    suspend fun searchPages(
        query: String? = null, directionAsc: Boolean = false, startCursor: String? = null, pageSize: Int? = null
    ): NotionResults<NotionPage>

    suspend fun searchDataSources(
        query: String? = null, directionAsc: Boolean = false, startCursor: String? = null, pageSize: Int? = null
    ): NotionResults<NotionDataSource>
}