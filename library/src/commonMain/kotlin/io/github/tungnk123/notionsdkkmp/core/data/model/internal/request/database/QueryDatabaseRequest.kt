package io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class QueryDatabaseRequest(
    @SerialName("start_cursor")
    val startCursor: String? = null,
    @SerialName("page_size")
    val pageSize: Int? = null,
)