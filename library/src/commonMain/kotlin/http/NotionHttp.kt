package http

import auth.TokenProvider
import core.data.model.NotionApiVersion
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private const val BASE_URL = "https://api.notion.com/v1/"
private const val HDR_NOTION_VERSION = "Notion-Version"

private val NotionJson = Json {
    ignoreUnknownKeys = true
    coerceInputValues = true
    isLenient = true
    explicitNulls = false
}

class NotionHttp(
    private val tokenProvider: TokenProvider,
    client: HttpClient,
    private val apiVersion: NotionApiVersion = NotionApiVersion.V2025_09_03,
    private val baseUrl: String = BASE_URL
) {
    @PublishedApi
    internal val httpClient: HttpClient = client.config {
        install(ContentNegotiation) { json(NotionJson) }
        defaultRequest {
            url(baseUrl)
            header(HDR_NOTION_VERSION, apiVersion.stringValue)
            header(HttpHeaders.Authorization, "Bearer ${tokenProvider.token()}")
            contentType(ContentType.Application.Json)
        }
        expectSuccess = true
    }

    suspend inline fun <reified T> get(path: String, noinline build: HttpRequestBuilder.() -> Unit = {}): T =
        httpClient.get(path, build).body()

    suspend inline fun <reified T> post(path: String, noinline build: HttpRequestBuilder.() -> Unit = {}): T =
        httpClient.post(path, build).body()

    suspend inline fun <reified T> patch(path: String, noinline build: HttpRequestBuilder.() -> Unit = {}): T =
        httpClient.patch(path, build).body()

    suspend inline fun <reified T> put(path: String, noinline build: HttpRequestBuilder.() -> Unit = {}): T =
        httpClient.put(path, build).body()

    suspend inline fun <reified T> delete(path: String, noinline build: HttpRequestBuilder.() -> Unit = {}): T =
        httpClient.delete(path, build).body()
}