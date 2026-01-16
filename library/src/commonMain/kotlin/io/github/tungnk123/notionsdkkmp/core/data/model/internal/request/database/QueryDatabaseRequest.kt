package io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.database

import io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.datasource.Sort
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class QueryDatabaseRequest(
    val filter: JsonObject? = null,
    val sorts: List<Sort>? = null,
    @SerialName("start_cursor")
    val startCursor: String? = null,
    @SerialName("page_size")
    val pageSize: Int? = null,
)