package service.search

import core.data.model.internal.dto.datasource.DataSourceDto
import core.data.model.internal.dto.page.PageDto
import core.data.model.internal.request.search.SearchRequest
import core.data.model.internal.response.ResultsResponseDto
import http.NotionHttp
import io.ktor.client.request.*

private object Routes {
    private const val SEARCH = "search"
    fun search() = SEARCH
}

class SearchServiceImpl(
    private val http: NotionHttp
) : SearchService {
    override suspend fun searchPages(request: SearchRequest): ResultsResponseDto<PageDto> =
        http.post(Routes.search()) { setBody(request) }

    override suspend fun searchDataSources(request: SearchRequest): ResultsResponseDto<DataSourceDto> =
        http.post(Routes.search()) { setBody(request) }
}
