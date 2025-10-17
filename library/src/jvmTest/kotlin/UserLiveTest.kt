import io.github.tungnk123.notionsdkkmp.auth.TokenProvider
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assume.assumeTrue
import io.github.tungnk123.notionsdkkmp.repository.user.UserRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.user.UserServiceImpl
import kotlin.test.Test
import kotlin.test.assertNotNull

class EnvTokenProvider(private val token: String) : TokenProvider {
    override fun token(): String = token
}

class UsersLiveTest {
    @Test
    fun me_live() {
        runBlocking {
            val token = System.getenv("NOTION_TOKEN")
            assumeTrue("NOTION_TOKEN is not set; skipping live test", !token.isNullOrBlank())

            val http = NotionHttp(EnvTokenProvider(token!!), HttpClient())
            val repo = UserRepositoryImpl(UserServiceImpl(http))

            val me = repo.me()
            assertNotNull(me.id)
        }
    }
}