package io.github.tungnk123.notionsdkkmp.core.data.model.result.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class NotionParent {
    @Serializable @SerialName("database_id")
    data class DatabaseId(@SerialName("database_id") val databaseId: String) : NotionParent()

    @Serializable @SerialName("data_source_id")
    data class DataSourceId(@SerialName("data_source_id") val dataSourceId: String) : NotionParent()

    @Serializable @SerialName("page_id")
    data class PageId(@SerialName("page_id") val pageId: String) : NotionParent()

    @Serializable @SerialName("block_id")
    data class BlockId(@SerialName("block_id") val blockId: String) : NotionParent()

    @Serializable @SerialName("workspace")
    data class Workspace(@SerialName("workspace") val workspace: Boolean = true) : NotionParent()
}
