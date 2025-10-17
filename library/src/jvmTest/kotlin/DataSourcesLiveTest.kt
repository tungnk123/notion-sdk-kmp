package datasource

import io.github.tungnk123.notionsdkkmp.auth.TokenProvider
import core.data.model.internal.dto.block.ParentDto
import core.data.model.internal.dto.datasource.DataSourcePropertyDto
import core.data.model.internal.dto.datasource.DataSourcePropertyType
import core.data.model.internal.dto.datasource.EmptyObj
import core.data.model.internal.request.datasource.CreateDataSourceRequest
import core.data.model.internal.request.datasource.QueryDataSourceRequest
import core.data.model.internal.request.datasource.UpdateDataSourceRequest
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assume.assumeTrue
import org.junit.Test
import io.github.tungnk123.notionsdkkmp.repository.datasource.DataSourceRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.datasource.DataSourceServiceImpl
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

private class EnvTokenProvider(private val token: String) : TokenProvider {
    override fun token(): String = token
}

class DataSourcesLiveTest {

    private fun getenv(name: String): String? =
        System.getenv(name) ?: System.getProperty(name)

    @Test
    fun retrieve_live() = runBlocking {
        val token = getenv("NOTION_TOKEN")
        assumeTrue("NOTION_TOKEN is not set; skipping live test", !token.isNullOrBlank())

        val dsId = getenv("NOTION_TEST_DATASOURCE_ID")
        assumeTrue("NOTION_TEST_DATASOURCE_ID is not set; skipping retrieve_live", !dsId.isNullOrBlank())

        val http = NotionHttp(EnvTokenProvider(token!!), HttpClient())
        val repo = DataSourceRepositoryImpl(DataSourceServiceImpl(http))

        val ds = repo.retrieve(dsId!!)
        assertNotNull(ds.createdTime)
        assertNotNull(ds.lastEditedTime)
        println("✅ retrieve_live OK: id=${ds.id}, properties=${ds.properties.keys}")
    }

    @Test
    fun update_add_url_property_live() = runBlocking {
        val token = getenv("NOTION_TOKEN")
        assumeTrue("NOTION_TOKEN is not set; skipping live test", !token.isNullOrBlank())

        val dsId = getenv("NOTION_TEST_DATASOURCE_ID")
        assumeTrue("NOTION_TEST_DATASOURCE_ID is not set; skipping update_add_url_property_live", !dsId.isNullOrBlank())

        val http = NotionHttp(EnvTokenProvider(token!!), HttpClient())
        val repo = DataSourceRepositoryImpl(DataSourceServiceImpl(http))

        val req = UpdateDataSourceRequest(
            properties = mapOf(
                "Website" to DataSourcePropertyDto(
                    id = "url",
                    name = "Website",
                    type = DataSourcePropertyType.URL,
                    url = EmptyObj
                )
            )
        )

        val updated = repo.update(dsId!!, req)
        println("✅ update_add_url_property_live OK: id=${updated.id}")
    }

    @Test
    fun query_live() = runBlocking {
        val token = getenv("NOTION_TOKEN")
        assumeTrue("NOTION_TOKEN is not set; skipping live test", !token.isNullOrBlank())

        val dsId = getenv("NOTION_TEST_DATASOURCE_ID")
        assumeTrue("NOTION_TEST_DATASOURCE_ID is not set; skipping query_live", !dsId.isNullOrBlank())

        val http = NotionHttp(EnvTokenProvider(token!!), HttpClient())
        val repo = DataSourceRepositoryImpl(DataSourceServiceImpl(http))

        val res = repo.query(dsId!!, QueryDataSourceRequest(pageSize = 1))
        assertNotNull(res.results)
        assertTrue(res.results.size >= 0)
        println("✅ query_live OK: results=${res.results.size}, hasMore=${res.hasMore}, nextCursor=${res.nextCursor}")
    }

    @Test
    fun create_live_minimal() = runBlocking {
        val token = getenv("NOTION_TOKEN")
        assumeTrue("NOTION_TOKEN is not set; skipping live test", !token.isNullOrBlank())

        val parentDbId = getenv("NOTION_PARENT_DATABASE_ID")
        assumeTrue("NOTION_PARENT_DATABASE_ID is not set; skipping create_live_minimal", !parentDbId.isNullOrBlank())

        val http = NotionHttp(EnvTokenProvider(token!!), HttpClient())
        val repo = DataSourceRepositoryImpl(DataSourceServiceImpl(http))

        val req = CreateDataSourceRequest(
            parent = ParentDto.DatabaseId(parentDbId!!),
            properties = mapOf(
                "Name" to DataSourcePropertyDto(
                    id = "title",
                    name = "Name",
                    type = DataSourcePropertyType.TITLE,
                    title = EmptyObj
                ),
                "Count" to DataSourcePropertyDto(
                    id = "number",
                    name = "Count",
                    type = DataSourcePropertyType.NUMBER,
                    number = core.data.model.internal.dto.datasource.NumberConfig()
                )
            )
        )

        val created = repo.create(req)
        assertNotNull(created.id)
        assertTrue(created.properties.isNotEmpty())
        println("✅ create_live_minimal OK: id=${created.id}, url=${created.url}")
    }
}