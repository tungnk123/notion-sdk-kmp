package di

import auth.StaticTokenProvider
import auth.TokenProvider
import http.NotionHttp
import io.ktor.client.*
import org.koin.core.module.Module
import org.koin.dsl.module
import repository.user.UsersRepository
import repository.user.UsersRepositoryImpl
import service.user.UsersService
import service.user.UsersServiceImpl

fun usersModule(token: String, httpClient: HttpClient? = null): Module = module {
    single<TokenProvider> { StaticTokenProvider(token) }
    single { httpClient ?: HttpClient() }
    single { NotionHttp(get(), get()) }
    single<UsersService> { UsersServiceImpl(get()) }
    single<UsersRepository> { UsersRepositoryImpl(get()) }
}
