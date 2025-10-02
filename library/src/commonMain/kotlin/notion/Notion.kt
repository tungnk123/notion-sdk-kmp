package notion

import core.data.model.NotionApiVersion
import core.data.model.result.block.NotionBlock
import core.data.model.result.database.NotionDatabaseSchema
import core.data.model.result.common.NotionResults
import core.data.model.result.page.NotionPage
import io.ktor.client.*
import io.ktor.utils.io.core.*
import kotlin.jvm.JvmStatic

interface Notion : Closeable {
    val token: String
    fun setHttpClient(newHttpClient: HttpClient)
    fun setToken(token: String)

    suspend fun queryDatabase(
        databaseId: String,
        startCursor: String? = null,
        pageSize: Int? = null,
    ): NotionResults<NotionPage>

    suspend fun queryDatabase(
        databaseId: String,
        jsonRequestBody: String,
    ): NotionResults<NotionPage>

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