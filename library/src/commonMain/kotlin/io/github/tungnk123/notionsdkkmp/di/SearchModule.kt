package io.github.tungnk123.notionsdkkmp.di

import org.koin.dsl.module
import io.github.tungnk123.notionsdkkmp.repository.search.SearchRepository
import io.github.tungnk123.notionsdkkmp.repository.search.SearchRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.search.SearchService
import io.github.tungnk123.notionsdkkmp.service.search.SearchServiceImpl

val searchModule = module {
    single<SearchService> { SearchServiceImpl(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get()) }
}
