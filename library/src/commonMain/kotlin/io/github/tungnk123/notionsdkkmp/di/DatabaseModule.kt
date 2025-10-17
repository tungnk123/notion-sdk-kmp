package io.github.tungnk123.notionsdkkmp.di

import org.koin.dsl.module
import io.github.tungnk123.notionsdkkmp.repository.database.DatabaseRepository
import io.github.tungnk123.notionsdkkmp.repository.database.DatabaseRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.database.DatabaseService
import io.github.tungnk123.notionsdkkmp.service.database.DatabaseServiceImpl

val databasesModule = module {
    single<DatabaseService> { DatabaseServiceImpl(get()) }
    single<DatabaseRepository> { DatabaseRepositoryImpl(get()) }
}
