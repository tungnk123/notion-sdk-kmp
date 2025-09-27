package core.data.mapper

import core.data.model.internal.response.ResultsResponse
import core.data.model.result.NotionResults

inline fun <T : Any, R : Any> ResultsResponse<T>.toDomain(
    map: (T) -> R
): NotionResults<R> = NotionResults(
    results = results.map(map), nextCursor = nextCursor, hasMore = hasMore
)
