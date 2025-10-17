package io.github.tungnk123.notionsdkkmp.repository.search

import io.github.tungnk123.notionsdkkmp.core.data.mapper.toDomain
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourceDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.page.PageDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.search.SearchRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.response.ResultsResponseDto
import io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionResults
import io.github.tungnk123.notionsdkkmp.core.data.model.result.datasource.NotionDataSource
import io.github.tungnk123.notionsdkkmp.core.data.model.result.page.NotionPage
import io.github.tungnk123.notionsdkkmp.service.search.SearchService

class SearchRepositoryImpl(
    private val service: SearchService
) : SearchRepository {

    override suspend fun searchPages(
        query: String?, directionAsc: Boolean, startCursor: String?, pageSize: Int?
    ): NotionResults<NotionPage> {
        val req = SearchRequest.forPages(
            query = query,
            direction = if (directionAsc) SearchRequest.Sort.Direction.Ascending else SearchRequest.Sort.Direction.Descending,
            startCursor = startCursor,
            pageSize = pageSize
        )
        val dto: ResultsResponseDto<PageDto> = service.searchPages(req)
        return NotionResults(
            results = dto.results.map { it.toDomain() },
            nextCursor = dto.nextCursor,
            hasMore = dto.hasMore
        )
    }

    override suspend fun searchDataSources(
        query: String?, directionAsc: Boolean, startCursor: String?, pageSize: Int?
    ): NotionResults<NotionDataSource> {
        val req = SearchRequest.forDataSources(
            query = query,
            direction = if (directionAsc) SearchRequest.Sort.Direction.Ascending else SearchRequest.Sort.Direction.Descending,
            startCursor = startCursor,
            pageSize = pageSize
        )
        val dto: ResultsResponseDto<DataSourceDto> = service.searchDataSources(req)
        return NotionResults(
            results = dto.results.map { it.toDomain() }, nextCursor = dto.nextCursor, hasMore = dto.hasMore
        )
    }
}
