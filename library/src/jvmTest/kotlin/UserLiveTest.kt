import auth.TokenProvider
import http.NotionHttp
import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Assume.assumeTrue
import repository.user.UsersRepositoryImpl
import service.user.UsersServiceImpl
import kotlin.test.Test
import kotlin.test.assertNotNull

private class EnvTokenProvider(private val token: String) : TokenProvider {
    override fun token(): String = token
}

class UsersLiveTest {
    @Test
    fun me_live() {
        runBlocking {
            val token = System.getenv("NOTION_TOKEN")
            assumeTrue("NOTION_TOKEN is not set; skipping live test", !token.isNullOrBlank())

            val http = NotionHttp(EnvTokenProvider(token!!), HttpClient())
            val repo = UsersRepositoryImpl(UsersServiceImpl(http))

            val me = repo.me()
            assertNotNull(me.id)
        }
    }
}