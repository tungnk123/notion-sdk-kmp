package io.github.tungnk123.notionsdkkmp.di

import io.github.tungnk123.notionsdkkmp.auth.OAuthTokenProvider
import io.github.tungnk123.notionsdkkmp.auth.repository.AuthRepository
import io.github.tungnk123.notionsdkkmp.auth.repository.AuthRepositoryFactory
import io.github.tungnk123.notionsdkkmp.auth.service.AuthService
import io.github.tungnk123.notionsdkkmp.auth.service.AuthServiceImpl
import io.github.tungnk123.notionsdkkmp.auth.tokenstorage.InMemoryMultiTokenStorage
import io.github.tungnk123.notionsdkkmp.auth.tokenstorage.InMemoryTokenStorage
import io.github.tungnk123.notionsdkkmp.auth.tokenstorage.MultiTokenStorage
import io.github.tungnk123.notionsdkkmp.auth.tokenstorage.TokenStorage
import io.ktor.client.*
import io.github.tungnk123.notionsdkkmp.notion.NotionSessionManager
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val authModule = module {
    single { HttpClient() }
    single<AuthService> { AuthServiceImpl(get()) }

    single<TokenStorage> { InMemoryTokenStorage() }
    single<MultiTokenStorage> { InMemoryMultiTokenStorage() }

    factory { (clientId: String, clientSecret: String) ->
        AuthRepository(
            service = get(), clientId = clientId, clientSecret = clientSecret, storage = get()
        )
    }

    factory { (repo: AuthRepository) -> OAuthTokenProvider(repo) }

    factory { (clientId: String, clientSecret: String) ->
        AuthRepositoryFactory(
            service = get(), clientId = clientId, clientSecret = clientSecret, multi = get()
        )
    }

    factory { (clientId: String, clientSecret: String) ->
        val authFactory = get<AuthRepositoryFactory> { parametersOf(clientId, clientSecret) }
        val tokenStore = get<MultiTokenStorage>()
        NotionSessionManager(
            authFactory = authFactory, tokenStore = tokenStore
        )
    }
}
