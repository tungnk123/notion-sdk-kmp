package notion

import core.data.model.NotionApiVersion
import core.data.model.result.NotionBlock
import core.data.model.result.NotionDatabaseRow
import core.data.model.result.NotionDatabaseSchema
import core.data.model.result.NotionResults
import io.ktor.client.*
import io.ktor.utils.io.core.Closeable
import kotlin.jvm.JvmStatic

interface Notion : Closeable {
    val token: String
    fun setHttpClient(newHttpClient: HttpClient)
    fun setToken(token: String)

    suspend fun queryDatabase(
        databaseId: String,
        startCursor: String? = null,
        pageSize: Int? = null,
    ): NotionResults<NotionDatabaseRow>

    suspend fun queryDatabase(
        databaseId: String,
        jsonRequestBody: String,
    ): NotionResults<NotionDatabaseRow>

    suspend fun retrieveDatabase(databaseId: String): NotionDatabaseSchema
    suspend fun retrieveBlock(blockId: String): NotionBlock
    suspend fun retrieveBlockChildren(
        blockId: String,
        startCursor: String? = null,
        pageSize: Int? = null,
    ): NotionResults<NotionBlock>

    companion object {
        @JvmStatic
        fun fromToken(
            token: String,
            version: NotionApiVersion = NotionApiVersion.LATEST,
            httpClient: HttpClient,
        ): Notion = NotionImpl(token, version, httpClient)
    }
}