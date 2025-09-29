package di

import org.koin.dsl.module
import repository.database.DatabaseRepository
import repository.database.DatabaseRepositoryImpl
import service.database.DatabaseService
import service.database.DatabaseServiceImpl

val databasesModule = module {
    single<DatabaseService> { DatabaseServiceImpl(get()) }
    single<DatabaseRepository> { DatabaseRepositoryImpl(get()) }
}
