package core.data.model.internal.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class QueryDatabaseRequestDto(
    @SerialName("start_cursor")
    val startCursor: String? = null,
    @SerialName("page_size")
    val pageSize: Int? = null,
)