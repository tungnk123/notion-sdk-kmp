package service.search

import core.data.model.internal.dto.datasource.DataSourceDto
import core.data.model.internal.dto.page.PageDto
import core.data.model.internal.request.search.SearchRequest
import core.data.model.internal.response.ResultsResponseDto

interface SearchService {
    suspend fun searchPages(request: SearchRequest): ResultsResponseDto<PageDto>
    suspend fun searchDataSources(request: SearchRequest): ResultsResponseDto<DataSourceDto>
}
