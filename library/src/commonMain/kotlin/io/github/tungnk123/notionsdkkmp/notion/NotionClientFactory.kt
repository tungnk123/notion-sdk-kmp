package io.github.tungnk123.notionsdkkmp.notion

import auth.OAuthTokenProvider
import auth.StaticTokenProvider
import auth.TokenProvider
import auth.repository.AuthRepository
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*

object NotionClientFactory {

    fun fromToken(
        token: String,
        httpClient: HttpClient = HttpClient(),
    ): NotionClient {
        val provider = StaticTokenProvider(token)
        val http = NotionHttp(provider, httpClient)
        return DefaultNotionClient(http)
    }

    fun fromTokenProvider(
        provider: TokenProvider,
        httpClient: HttpClient = HttpClient(),
    ): NotionClient {
        val http = NotionHttp(provider, httpClient)
        return DefaultNotionClient(http)
    }

    fun fromAuthRepository(
        repo: AuthRepository,
        httpClient: HttpClient = HttpClient(),
    ): NotionClient {
        val provider = OAuthTokenProvider(repo)
        val http = NotionHttp(provider, httpClient)
        return DefaultNotionClient(http)
    }
}
