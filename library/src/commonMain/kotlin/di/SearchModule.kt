package di

import org.koin.dsl.module
import repository.search.SearchRepository
import repository.search.SearchRepositoryImpl
import service.search.SearchService
import service.search.SearchServiceImpl

val searchModule = module {
    single<SearchService> { SearchServiceImpl(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get()) }
}
