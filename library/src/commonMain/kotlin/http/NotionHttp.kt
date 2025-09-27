package http

import auth.TokenProvider
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class NotionHttp(
    private val tokenProvider: TokenProvider,
    client: HttpClient? = null
) {
    private val http = (client ?: HttpClient()).config {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true; classDiscriminator = "type"; explicitNulls = false })
        }
        defaultRequest {
            url("https://api.notion.com/v1/")
            header("Notion-Version", "2025-09-03")
            header(HttpHeaders.Authorization, "Bearer ${tokenProvider.token()}")
            contentType(ContentType.Application.Json)
        }
        expectSuccess = true
    }

    private suspend inline fun <reified T> get(path: String, noinline build: HttpRequestBuilder.() -> Unit = {}) =
        http.get(path, build).body<T>()
}
