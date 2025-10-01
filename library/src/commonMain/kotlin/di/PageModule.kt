package di

import org.koin.dsl.module
import repository.page.PageRepository
import repository.page.PageRepositoryImpl
import service.page.PageService
import service.page.PageServiceImpl

val pagesModule = module {
    single<PageService> { PageServiceImpl(get()) }
    single<PageRepository> { PageRepositoryImpl(get()) }
}
