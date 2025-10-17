import auth.TokenProvider
import core.data.model.result.datasource.NotionDataSource
import core.data.model.result.page.NotionPage
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assume.assumeTrue
import org.junit.Test
import io.github.tungnk123.notionsdkkmp.repository.search.SearchRepository
import io.github.tungnk123.notionsdkkmp.repository.search.SearchRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.search.SearchService
import io.github.tungnk123.notionsdkkmp.service.search.SearchServiceImpl
import kotlin.test.assertNotNull

private class DirectTokenProvider(private val t: String) : TokenProvider {
    override fun token(): String = t
}

class SearchLiveTest {

    private fun env(name: String): String? =
        System.getenv(name) ?: System.getProperty(name)

    private fun newHttp(): NotionHttp {
        val token = env("NOTION_TOKEN")
        assumeTrue("NOTION_TOKEN is not set; skipping SearchLiveTest", !token.isNullOrBlank())
        return NotionHttp(DirectTokenProvider(token!!), HttpClient())
    }

    private fun repo(): SearchRepository {
        val http = newHttp()
        val service: SearchService = SearchServiceImpl(http)
        return SearchRepositoryImpl(service)
    }

    @Test
    fun search_pages_live_basic() = runBlocking {
        val response = repo().searchPages(query = null, directionAsc = false, startCursor = null, pageSize = 10)
        if (response.results.isNotEmpty()) {
            val first: NotionPage = response.results.first()
            assertNotNull(first.id)
        }
        println("✅ search_pages_live_basic size=${response.results.size} hasMore=${response.hasMore}")
    }

    @Test
    fun search_pages_by_query_live() = runBlocking {
        val response = repo().searchPages(query = "Test",directionAsc = true, startCursor = null, pageSize = 10)
        if (response.results.isNotEmpty()) {
            val first: NotionPage = response.results.first()
            assertNotNull(first.id)
        }
        println("✅ search_pages_by_query_live query=Test size=${response.results.size} hasMore=${response.hasMore}")
    }

    @Test
    fun search_data_sources_live_basic() = runBlocking {
        val response = repo().searchDataSources(query = null, directionAsc = false, startCursor = null, pageSize = 5)
        if (response.results.isNotEmpty()) {
            val first: NotionDataSource = response.results.first()
            assertNotNull(first.id)
        }
        println("✅ search_data_sources_live_basic size=${response.results.size} hasMore=${response.hasMore}")
    }

    @Test
    fun search_data_sources_by_query_live() = runBlocking {
        val response = repo().searchDataSources(query = "Test", directionAsc = false, startCursor = null, pageSize = 5)
        if (response.results.isNotEmpty()) {
            val first: NotionDataSource = response.results.first()
            assertNotNull(first.id)
        }
        println("✅ search_data_sources_by_query_live query=Test size=${response.results.size} hasMore=${response.hasMore}")
    }
}
