package repository.search

import core.data.mapper.toDomain
import core.data.model.internal.dto.datasource.DataSourceDto
import core.data.model.internal.dto.page.PageDto
import core.data.model.internal.request.search.SearchRequest
import core.data.model.internal.response.ResultsResponseDto
import core.data.model.result.common.NotionResults
import core.data.model.result.datasource.NotionDataSource
import core.data.model.result.page.NotionPage
import service.search.SearchService

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
