import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import io.github.tungnk123.notionsdkkmp.notion.NotionClientFactory

fun main() = runBlocking {
    val token = System.getenv("NOTION_TOKEN") ?: System.getProperty("NOTION_TOKEN")
    println("token=${token}")
    val client = NotionClientFactory.fromToken(token, HttpClient())

    val pageId = System.getenv("NOTION_TEST_PAGE_ID") ?: return@runBlocking
    val page = client.pageRepository.retrieve(pageId)
    println("page=$page")

    val dbId = System.getenv("NOTION_TEST_DATABASE_ID") ?: return@runBlocking
    val db = client.databaseRepository.retrieve(dbId)
    println("database=$db")

    val results = client.searchRepository.searchPages(query = "Books")
    println("search.pages=${results.results}")
}