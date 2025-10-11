package sample
import auth.model.OAuthTokenResponse
import auth.repository.AuthRepository
import auth.service.AuthServiceImpl
import auth.tokenstorage.InMemoryTokenStorage
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import notion.NotionClientFactory

fun main() = runBlocking {
    val clientId = System.getenv("NOTION_CLIENT_ID") ?: error("NOTION_CLIENT_ID not set")
    val clientSecret = System.getenv("NOTION_CLIENT_SECRET") ?: error("NOTION_CLIENT_SECRET not set")
    val accessToken = System.getenv("NOTION_ACCESS_TOKEN") ?: error("NOTION_ACCESS_TOKEN not set")

    val storage = InMemoryTokenStorage().apply {
        set(
            OAuthTokenResponse(
                accessToken = accessToken,
                tokenType = "bearer",
                workspaceId = "w",
                workspaceName = "Demo",
                botId = null,
                owner = null
            )
        )
    }

    val repo = AuthRepository(AuthServiceImpl(HttpClient()), clientId, clientSecret, storage)
    val client = NotionClientFactory.fromAuthRepository(repo, HttpClient())

    val me = client.userRepository.me()
    println("me=${me.id}")

    val dsResults = client.searchRepository.searchDataSources(query = "External")
    println("search.datasources.size=${dsResults.results.size}")
}
