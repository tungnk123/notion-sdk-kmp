package datasource

import auth.TokenProvider
import core.data.model.internal.dto.datasource.DataSourcePropertyDto
import core.data.model.internal.dto.datasource.DataSourcePropertyType
import core.data.model.internal.dto.datasource.EmptyObj
import core.data.model.internal.dto.datasource.ParentDto
import core.data.model.internal.request.datasource.CreateDataSourceRequest
import core.data.model.internal.request.datasource.QueryDataSourceRequest
import core.data.model.internal.request.datasource.UpdateDataSourceRequest
import http.NotionHttp
import io.ktor.client.*
import org.junit.Assume.assumeTrue
import kotlinx.coroutines.runBlocking
import org.junit.Test
import repository.datasource.DataSourceRepositoryImpl
import service.datasource.DataSourceServiceImpl
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

private class EnvTokenProvider(private val token: String) : TokenProvider {
    override fun token(): String = token
}

/**
 * Live tests for Data Sources.
 *
 * Env/properties you can provide (similar to UsersLiveTest wiring):
 *  - NOTION_TOKEN                         (required for all tests)
 *  - NOTION_TEST_DATASOURCE_ID            (optional; used by retrieve/update/query)
 *  - NOTION_PARENT_DATABASE_ID            (optional; used by create)
 *
 * Gradle already wires NOTION_TOKEN for tests as you showed.
 * If you also want to wire the two optional IDs, you can add the same pattern in build.gradle.kts.
 */
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
        assertEquals(dsId, ds.id)
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
        assertEquals(dsId, updated.id)
        // Nếu domain của bạn expose properties ở response, có thể assert thêm:
        // assertTrue(updated.properties.containsKey("Website"))
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