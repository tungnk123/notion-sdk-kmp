package io.github.tungnk123.notionsdkkmp.core.data.model.result.common

import io.github.tungnk123.notionsdkkmp.core.data.model.serializer.NotionResultsTypedSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = NotionResultsTypedSerializer::class)
data class NotionResults<T>(
    val results: List<T>,
    @SerialName("next_cursor")
    val nextCursor: String? = null,
    @SerialName("has_more")
    val hasMore: Boolean,
)