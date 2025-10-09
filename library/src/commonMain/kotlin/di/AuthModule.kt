package di

import auth.OAuthTokenProvider
import auth.repository.AuthRepository
import auth.repository.AuthRepositoryFactory
import auth.service.AuthService
import auth.service.AuthServiceImpl
import auth.tokenstorage.InMemoryMultiTokenStorage
import auth.tokenstorage.InMemoryTokenStorage
import auth.tokenstorage.MultiTokenStorage
import auth.tokenstorage.TokenStorage
import io.ktor.client.*
import notion.NotionSessionManager
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
