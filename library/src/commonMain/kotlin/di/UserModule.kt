package di

import auth.StaticTokenProvider
import auth.TokenProvider
import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.ktor.client.*
import org.koin.core.module.Module
import org.koin.dsl.module
import io.github.tungnk123.notionsdkkmp.repository.user.UserRepository
import io.github.tungnk123.notionsdkkmp.repository.user.UserRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.user.UserService
import io.github.tungnk123.notionsdkkmp.service.user.UserServiceImpl

fun usersModule(token: String, httpClient: HttpClient? = null): Module = module {
    single<TokenProvider> { StaticTokenProvider(token) }
    single { httpClient ?: HttpClient() }
    single { NotionHttp(get(), get()) }
    single<UserService> { UserServiceImpl(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}
