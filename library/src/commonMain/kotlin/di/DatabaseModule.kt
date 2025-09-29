package di

import org.koin.dsl.module
import repository.database.DatabasesRepository
import repository.database.DatabasesRepositoryImpl
import service.database.DatabaseService
import service.database.DatabaseServiceImpl

val databasesModule = module {
    single<DatabaseService> { DatabaseServiceImpl(get()) }
    single<DatabasesRepository> { DatabasesRepositoryImpl(get()) }
}
