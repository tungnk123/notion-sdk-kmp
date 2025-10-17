@file:OptIn(ExperimentalSerializationApi::class)

package io.github.tungnk123.notionsdkkmp.core.data.model.internal.request.search

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRequest(
    val query: String? = null,
    val sort: Sort? = null,
    val filter: Filter? = null,
    @SerialName("start_cursor") val startCursor: String? = null,
    @SerialName("page_size") val pageSize: Int? = null
) {
    @Serializable
    data class Sort(
        val direction: Direction,
        @EncodeDefault(EncodeDefault.Mode.ALWAYS)
        val timestamp: Timestamp = Timestamp.LastEditedTime
    ) {
        @Serializable
        enum class Direction {
            @SerialName("ascending")
            Ascending,
            @SerialName("descending")
            Descending
        }

        @Serializable
        enum class Timestamp {
            @SerialName("last_edited_time")
            LastEditedTime
        }
    }

    @Serializable
    data class Filter(
        val value: Value,
        @EncodeDefault(EncodeDefault.Mode.ALWAYS)
        val property: Property = Property.Object
    ) {
        @Serializable
        enum class Value {
            @SerialName("page")
            Page,
            @SerialName("data_source")
            DataSource
        }

        @Serializable
        enum class Property {
            @SerialName("object")
            Object
        }
    }

    companion object {
        fun forPages(
            query: String? = null,
            direction: Sort.Direction = Sort.Direction.Descending,
            startCursor: String? = null,
            pageSize: Int? = null
        ) = SearchRequest(
            query = query,
            sort = Sort(direction = direction, timestamp = Sort.Timestamp.LastEditedTime),
            filter = Filter(value = Filter.Value.Page, property = Filter.Property.Object),
            startCursor = startCursor,
            pageSize = pageSize
        )

        fun forDataSources(
            query: String? = null,
            direction: Sort.Direction = Sort.Direction.Descending,
            startCursor: String? = null,
            pageSize: Int? = null
        ) = SearchRequest(
            query = query,
            sort = Sort(direction = direction, timestamp = Sort.Timestamp.LastEditedTime),
            filter = Filter(value = Filter.Value.DataSource, property = Filter.Property.Object),
            startCursor = startCursor,
            pageSize = pageSize
        )
    }
}
