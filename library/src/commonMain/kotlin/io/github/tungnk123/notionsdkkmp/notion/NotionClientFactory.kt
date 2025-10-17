package io.github.tungnk123.notionsdkkmp.notion

import io.github.tungnk123.notionsdkkmp.auth.OAuthTokenProvider
import io.github.tungnk123.notionsdkkmp.auth.StaticTokenProvider
import io.github.tungnk123.notionsdkkmp.auth.TokenProvider
import io.github.tungnk123.notionsdkkmp.auth.repository.AuthRepository
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
