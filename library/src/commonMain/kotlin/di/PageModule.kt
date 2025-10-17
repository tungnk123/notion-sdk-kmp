package di

import org.koin.dsl.module
import io.github.tungnk123.notionsdkkmp.repository.page.PageRepository
import io.github.tungnk123.notionsdkkmp.repository.page.PageRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.page.PageService
import io.github.tungnk123.notionsdkkmp.service.page.PageServiceImpl

val pagesModule = module {
    single<PageService> { PageServiceImpl(get()) }
    single<PageRepository> { PageRepositoryImpl(get()) }
}
