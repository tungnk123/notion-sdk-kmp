package users

import io.github.tungnk123.notionsdkkmp.auth.TokenProvider
import io.github.tungnk123.notionsdkkmp.core.data.model.result.common.NotionResults
import io.github.tungnk123.notionsdkkmp.core.data.model.result.user.NotionUser
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import io.github.tungnk123.notionsdkkmp.repository.user.UserRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.user.UserService
import io.github.tungnk123.notionsdkkmp.service.user.UserServiceImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

private class TestToken : TokenProvider {
    override fun token() = "x"
}

class UsersRepositoryTest {
    private fun mockClient(): HttpClient {
        val meJson = """
          {"object":"user","id":"u_me","type":"person","name":"Me","avatar_url":null,"person":{"email":"me@example.com"}}
        """.trimIndent()

        val userJson = """
          {"object":"user","id":"u_123","type":"bot","name":"My Bot","avatar_url":null,"bot":{"workspace_name":"WS"}}
        """.trimIndent()

        val listJson = """
          {
            "object":"list",
            "results":[
              {"object":"user","id":"u_1","type":"person","name":"Ada","avatar_url":null,"person":{"email":"ada@ex.com"}},
              {"object":"user","id":"u_2","type":"bot","name":"Botty","avatar_url":null,"bot":{"workspace_name":"WS"}}
            ],
            "next_cursor":null,
            "has_more":false
          }
        """.trimIndent()

        val engine = MockEngine { req ->
            val path = req.url.encodedPath
            val body = when {
                path.endsWith("/users/me") -> meJson
                path.endsWith("/users/u_123") -> userJson
                path.endsWith("/users") -> listJson
                else -> error("Unhandled path: $path")
            }
            respond(
                content = body, headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        return HttpClient(engine)
    }

    @Test
    fun `users me maps to domain`() = runTest {
        val http = NotionHttp(TestToken(), mockClient())
        val svc: UserService = UserServiceImpl(http)
        val repo = UserRepositoryImpl(svc)

        val me: NotionUser = repo.me()
        assertTrue(me is NotionUser.Person)
        assertEquals("u_me", me.id)
        assertEquals("Me", me.name)
        assertEquals("me@example.com", me.email)
    }

    @Test
    fun `users retrieve maps bot`() = runTest {
        val http = NotionHttp(TestToken(), mockClient())
        val repo = UserRepositoryImpl(UserServiceImpl(http))

        val u: NotionUser = repo.retrieve("u_123")
        assertTrue(u is NotionUser.Bot)
        assertEquals("u_123", u.id)
        assertEquals("My Bot", u.name)
    }

    @Test
    fun `users list returns two results`() = runTest {
        val http = NotionHttp(TestToken(), mockClient())
        val repo = UserRepositoryImpl(UserServiceImpl(http))

        val res: NotionResults<NotionUser> = repo.list()
        assertEquals(2, res.results.size)
        assertTrue(!res.hasMore && res.nextCursor == null)
        assertTrue(res.results[0] is NotionUser.Person)
        assertTrue(res.results[1] is NotionUser.Bot)
    }
}
