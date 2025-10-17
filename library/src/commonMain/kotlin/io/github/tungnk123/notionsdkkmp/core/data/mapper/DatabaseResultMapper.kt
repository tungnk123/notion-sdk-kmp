package io.github.tungnk123.notionsdkkmp.core.data.mapper

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.response.ResultsResponseDto
import io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionResults

inline fun <T : Any, R : Any> ResultsResponseDto<T>.toDomain(
    map: (T) -> R
): NotionResults<R> =
    NotionResults(
        results = results.map(map), nextCursor = nextCursor, hasMore = hasMore
    )
