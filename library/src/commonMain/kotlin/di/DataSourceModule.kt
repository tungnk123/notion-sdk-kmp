package di

import org.koin.dsl.module
import io.github.tungnk123.notionsdkkmp.repository.datasource.DataSourceRepository
import io.github.tungnk123.notionsdkkmp.repository.datasource.DataSourceRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.datasource.DataSourceService
import io.github.tungnk123.notionsdkkmp.service.datasource.DataSourceServiceImpl

val dataSourcesModule = module {
    single<DataSourceService> { DataSourceServiceImpl(get()) }
    single<DataSourceRepository> { DataSourceRepositoryImpl(get()) }
}
