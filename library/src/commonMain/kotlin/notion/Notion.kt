package notion

import core.data.model.NotionApiVersion
import core.data.model.result.NotionBlock
import core.data.model.result.NotionDatabaseRow
import core.data.model.result.NotionDatabaseSchema
import core.data.model.result.NotionResults
import io.ktor.client.*
import io.ktor.utils.io.core.*
import kotlin.jvm.JvmStatic

interface Notion : Closeable {
    val token: String
    fun setHttpClient(newHttpClient: HttpClient)
    fun setToken(token: String)

    /**
     * @see <a href="https://developers.notion.com/reference/post-database-query">Notion documentation</a>
     */
    suspend fun queryDatabase(
        databaseId: String,
        startCursor: String? = null,
        pageSize: Int? = null,
    ): NotionResults<NotionDatabaseRow>

    /**
     * Notion API filter & sort params are too complicated to cover all the cases via strictly-typed models.
     *
     * @param jsonRequestBody Will be sent as the JSON request body.
     * @see <a href="https://developers.notion.com/reference/post-database-query">Notion documentation</a>
     */
    suspend fun queryDatabase(
        databaseId: String,
        jsonRequestBody: String,
    ): NotionResults<NotionDatabaseRow>

    /**
     * @see <a href="https://developers.notion.com/reference/retrieve-a-database">Notion documentation</a>
     */
    suspend fun retrieveDatabase(
        databaseId: String,
    ): NotionDatabaseSchema

    /**
     * @see <a href="https://developers.notion.com/reference/retrieve-a-block">Notion documentation</a>
     */
    suspend fun retrieveBlock(
        blockId: String,
    ): NotionBlock

    /**
     * @see <a href="https://developers.notion.com/reference/get-block-children">Notion documentation</a>
     */
    suspend fun retrieveBlockChildren(
        blockId: String,
        startCursor: String? = null,
        pageSize: Int? = null,
    ): NotionResults<NotionBlock>

    companion object {
        const val HEADER_VERSION: String = "Notion-Version"
        const val API_BASE_URL: String = "https://api.notion.com/v1"

        @JvmStatic
        fun fromToken(
            token: String,
            version: NotionApiVersion = NotionApiVersion.LATEST,
            httpClient: HttpClient,
        ): Notion =
            NotionImpl(token, version, httpClient)
    }
}