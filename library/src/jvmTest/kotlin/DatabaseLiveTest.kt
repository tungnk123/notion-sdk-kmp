import core.data.model.internal.dto.block.ParentDto
import core.data.model.internal.dto.datasource.DataSourcePropertyDto
import core.data.model.internal.dto.datasource.DataSourcePropertyType
import core.data.model.internal.dto.datasource.EmptyObj
import core.data.model.internal.request.database.CreateDatabaseRequest
import core.data.model.internal.request.database.InitialDataSourceRequest
import core.data.model.internal.request.database.UpdateDatabaseRequest
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Assume.assumeTrue
import org.junit.Test
import io.github.tungnk123.notionsdkkmp.repository.database.DatabaseRepository
import io.github.tungnk123.notionsdkkmp.repository.database.DatabaseRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.database.DatabaseService
import io.github.tungnk123.notionsdkkmp.service.database.DatabaseServiceImpl
import kotlin.test.assertNotNull

class DatabaseLiveTest {

    private fun env(name: String): String? =
        System.getenv(name) ?: System.getProperty(name)

    private fun newHttp(): NotionHttp {
        val token = env("NOTION_TOKEN")
        assumeTrue("NOTION_TOKEN is not set; skipping live test", !token.isNullOrBlank())
        return NotionHttp(EnvTokenProvider(token!!), HttpClient())
    }

    private fun repo(): DatabaseRepository {
        val http = newHttp()
        val service: DatabaseService = DatabaseServiceImpl(http)
        return DatabaseRepositoryImpl(service)
    }

    @Test
    fun retrieve_live() = runBlocking {
        val dbId = env("NOTION_TEST_DATABASE_ID")
        assumeTrue("NOTION_TEST_DATABASE_ID is not set; skipping retrieve_live", !dbId.isNullOrBlank())

        val r = repo().retrieve(dbId!!)
        assertNotNull(r.createdTime)
        assertNotNull(r.lastEditedTime)
        println("✅ retrieve_live: id=${r.id}, title=${r.title.joinToString { it.plainText }}, dataSources=${r.dataSources.size}")
    }

    @Test
    fun create_live_minimal() = runBlocking {
        val parentPageId = env("NOTION_PARENT_PAGE_ID")
        assumeTrue("NOTION_PARENT_PAGE_ID is not set; skipping create_live_minimal", !parentPageId.isNullOrBlank())

        val req = CreateDatabaseRequest(
            parent = ParentDto.PageId(parentPageId!!),
            initialDataSource = InitialDataSourceRequest(
                properties = mapOf(
                    "Name" to DataSourcePropertyDto(
                        id = "title",
                        name = "Name",
                        type = DataSourcePropertyType.TITLE,
                        title = EmptyObj
                    )
                )
            ),
            title = null,
            description = null,
            icon = null,
            cover = null
        )

        val created = repo().create(req)
        assertNotNull(created.id)
        println("✅ create_live_minimal: id=${created.id}, dataSources=${created.dataSources.size}")
    }

    @Test
    fun update_parent_live() = runBlocking {
        val dbId = env("NOTION_TEST_DATABASE_ID")
        assumeTrue("NOTION_TEST_DATABASE_ID is not set; skipping update_parent_live", !dbId.isNullOrBlank())

        val parentPageId = env("NOTION_PARENT_PAGE_ID")
        assumeTrue("NOTION_PARENT_PAGE_ID is not set; skipping update_parent_live", !parentPageId.isNullOrBlank())

        val req = UpdateDatabaseRequest(
            parent = ParentDto.PageId(parentPageId!!)
        )

        val updated = repo().update(dbId!!, req)
        println("✅ update_parent_live: id=${updated.id}")
        assertTrue(updated.title.isNotEmpty())
    }

    @Test
    fun retrieve_database_live() = runBlocking {
        val dbId = env("NOTION_TEST_DATABASE_ID")
        assumeTrue("NOTION_TEST_DATABASE_ID is not set; skipping retrieve_database_live", !dbId.isNullOrBlank())

        val db = repo().retrieve(dbId!!)
        println("✅ retrieve_database_live: id=${db.id}, title=${db.title.joinToString { it.plainText }}")
        assertTrue(db.title.isNotEmpty())
    }
}
