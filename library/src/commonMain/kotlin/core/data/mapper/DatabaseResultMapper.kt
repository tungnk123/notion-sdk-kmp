package core.data.mapper

import core.data.model.internal.obj.BlockObject
import core.data.model.internal.response.PageObject
import core.data.model.internal.response.ResultsResponse
import core.data.model.result.NotionBlock
import core.data.model.result.NotionDatabaseRow
import core.data.model.result.NotionResults

@Suppress("UNCHECKED_CAST")
internal inline fun <reified T : Any, reified R : Any> ResultsResponse<T>.toDomain(): NotionResults<R> = NotionResults(
    results = (when (T::class) {
        PageObject::class -> when (R::class) {
            NotionDatabaseRow::class -> results.filterIsInstance<PageObject>().map(PageObject::toDomain)
            else -> null
        }

        BlockObject::class -> when (R::class) {
            NotionBlock::class -> results.filterIsInstance<BlockObject>().map(BlockObject::toDomain)
            else -> null
        }

        else -> error("${T::class} results response domain mapping is not supported")
    } ?: error("${T::class} -> ${R::class} results response domain mapping is not supported")) as List<R>,
    nextCursor = nextCursor,
    hasMore = hasMore,
)