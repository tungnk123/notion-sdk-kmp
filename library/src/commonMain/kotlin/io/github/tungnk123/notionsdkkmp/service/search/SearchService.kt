package io.github.tungnk123.notionsdkkmp.service.search

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.datasource.DataSourceDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.dto.page.PageDto
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.search.SearchRequest
import io.github.tungnk123.notionsdkkmp.core.data.model.internal.response.ResultsResponseDto

interface SearchService {
    suspend fun searchPages(request: SearchRequest): ResultsResponseDto<PageDto>
    suspend fun searchDataSources(request: SearchRequest): ResultsResponseDto<DataSourceDto>
}
