package notion

import auth.OAuthTokenProvider
import auth.repository.AuthRepository
import http.NotionClientConfig
import http.NotionHttp
import http.installNotionDefaults
import io.ktor.client.*
import repository.page.PageRepository
import repository.page.PageRepositoryImpl
import service.page.PageServiceImpl

class NotionClient(
    val http: NotionHttp, val pages: PageRepository
)

fun buildNotionClient(authRepo: AuthRepository): NotionClient {
    val provider = OAuthTokenProvider(authRepo)
    val httpClient =
        HttpClient { installNotionDefaults(NotionClientConfig(tokenProvider = provider, authRepository = authRepo)) }
    val notionHttp = NotionHttp(provider, httpClient)
    val pages = PageRepositoryImpl(PageServiceImpl(notionHttp))
    return NotionClient(notionHttp, pages)
}
