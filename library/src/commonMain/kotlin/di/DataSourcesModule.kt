package di

import org.koin.dsl.module
import repository.datasource.DataSourcesRepository
import repository.datasource.DataSourcesRepositoryImpl
import service.datasource.DataSourcesService
import service.datasource.DataSourcesServiceImpl

val dataSourcesModule = module {
    single<DataSourcesService> { DataSourcesServiceImpl(get()) }
    single<DataSourcesRepository> { DataSourcesRepositoryImpl(get()) }
}
