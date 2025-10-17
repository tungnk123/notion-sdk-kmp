import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import io.github.tungnk123.notionsdkkmp.notion.NotionClientFactory

fun main() = runBlocking {
    val token = System.getenv("NOTION_TOKEN")
        ?: System.getProperty("NOTION_TOKEN")
        ?: error("❌ Missing NOTION_TOKEN")

    println("token in SampleMain=${token}")
    val client = NotionClientFactory.fromToken(token, HttpClient())
    println("✅ Notion client created: $client")
}