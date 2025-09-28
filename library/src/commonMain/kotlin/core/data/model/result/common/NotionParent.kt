package core.data.model.result.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class NotionParent {
    @Serializable @SerialName("database_id")
    data class DatabaseId(@SerialName("database_id") val databaseId: String) : NotionParent()
    @Serializable @SerialName("page_id")
    data class PageId(@SerialName("page_id") val pageId: String) : NotionParent()
}
