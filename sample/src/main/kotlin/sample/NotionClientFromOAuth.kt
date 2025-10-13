import auth.repository.AuthRepository
import auth.service.AuthServiceImpl
import auth.tokenstorage.InMemoryTokenStorage
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import notion.NotionClientFactory
import java.awt.Desktop
import java.net.URI

fun main() = runBlocking {
    val clientId = env("NOTION_CLIENT_ID")
    val clientSecret = env("NOTION_CLIENT_SECRET")
    val redirectUri = env("NOTION_REDIRECT_URI")

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                coerceInputValues = true
            })
        }
    }

    val repo = AuthRepository(
        AuthServiceImpl(httpClient),
        clientId,
        clientSecret,
        InMemoryTokenStorage()
    )

    val state = "state_${System.currentTimeMillis()}"
    val authorizeUrl = repo.authorizeUrl(redirectUri, state, false)
    val codeDeferred = CompletableDeferred<String>()

    val server = embeddedServer(Netty, port = URI(redirectUri).port.takeIf { it > 0 } ?: 54321) {
        install(StatusPages) {
            exception<Throwable> { call, cause ->
                cause.printStackTrace()
                call.respondText("Error: ${cause.message}")
            }
        }
        routing {
            get("/callback") {
                val code = call.request.queryParameters["code"] ?: error("Missing code")
                val st = call.request.queryParameters["state"] ?: error("Missing state")
                require(st == state) { "State mismatch" }
                codeDeferred.complete(code)
                call.respondText("✅ Authorized. You can close this window.")
            }
        }
    }.start(wait = false)

    println("Opening browser for authorization...")
    open(authorizeUrl)

    val code = codeDeferred.await()
    println("Authorization code received, exchanging for token...")

    val token = repo.exchange(code, redirectUri)
    println("✅ Token received!")
    println("   Access Token: ${token.accessToken.take(12)}...")
    println("   Workspace: ${token.workspaceName}")

    val client = NotionClientFactory.fromAuthRepository(repo, httpClient)

    println("\nFetching user info...")
    val me = client.userRepository.me()
    println("   User: ${me.name}")

    println("\nSearching for pages with 'test'...")
    val results = client.searchRepository.searchPages(query = "test")
    println("   Found ${results.results.size} pages")

    httpClient.close()
    server.stop()
    println("\n✅ Done!")
}

private fun env(key: String): String {
    System.getenv(key)?.let { return it }
    System.getProperty(key)?.let { return it }

    val file = java.io.File("local.properties")
    if (file.exists()) {
        val properties = java.util.Properties()
        java.io.FileInputStream(file).use { properties.load(it) }
        properties.getProperty(key)?.let { return it }
    }

    error("Missing $key")
}

private fun open(url: String) {
    if (Desktop.isDesktopSupported()) {
        Desktop.getDesktop().browse(URI(url))
    } else {
        println("Open manually: $url")
    }
}