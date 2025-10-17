import io.github.tungnk123.notionsdkkmp.auth.TokenProvider
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import io.github.tungnk123.notionsdkkmp.notion.NotionClientFactory

class EnvTokenProvider : TokenProvider {
    override fun token(): String =
        System.getenv("NOTION_TOKEN") ?: error("NOTION_TOKEN not set")
}

fun main() = runBlocking {
    val provider = EnvTokenProvider()
    val client = NotionClientFactory.fromTokenProvider(provider, HttpClient())
    println(client)

    val pageId = System.getenv("NOTION_TEST_PAGE_ID") ?: return@runBlocking
    val page = client.pageRepository.retrieve(pageId)
    println("page=${page.id}")

    val dbId = System.getenv("NOTION_TEST_DATABASE_ID") ?: return@runBlocking
    val db = client.databaseRepository.retrieve(dbId)
    println("database=${db.id}")

    val results = client.searchRepository.searchPages(query = "Books")
    println("search.pages.size=${results.results.size}")
}