package di

import auth.*
import auth.repository.AuthRepository
import auth.service.AuthService
import auth.service.AuthServiceImpl
import io.ktor.client.*
import org.koin.dsl.module

val authModule = module {
    single { HttpClient() }
    single<AuthService> { AuthServiceImpl(get()) }
    single<TokenStorage> { InMemoryTokenStorage() }
    single { (clientId: String, clientSecret: String) ->
        AuthRepository(service = get(), clientId = clientId, clientSecret = clientSecret, storage = get())
    }
    factory { (repo: AuthRepository) -> OAuthTokenProvider(repo) }
}
