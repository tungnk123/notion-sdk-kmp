package di

import org.koin.dsl.module
import repository.datasource.DataSourceRepository
import repository.datasource.DataSourceRepositoryImpl
import service.datasource.DataSourceService
import service.datasource.DataSourceServiceImpl

val dataSourcesModule = module {
    single<DataSourceService> { DataSourceServiceImpl(get()) }
    single<DataSourceRepository> { DataSourceRepositoryImpl(get()) }
}
