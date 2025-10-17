package io.github.tungnk123.notionsdkkmp.notion

import io.github.tungnk123.notionsdkkmp.http.NotionHttp
import io.github.tungnk123.notionsdkkmp.repository.block.BlockRepository
import io.github.tungnk123.notionsdkkmp.repository.block.BlockRepositoryImpl
import io.github.tungnk123.notionsdkkmp.repository.database.DatabaseRepository
import io.github.tungnk123.notionsdkkmp.repository.database.DatabaseRepositoryImpl
import io.github.tungnk123.notionsdkkmp.repository.datasource.DataSourceRepository
import io.github.tungnk123.notionsdkkmp.repository.datasource.DataSourceRepositoryImpl
import io.github.tungnk123.notionsdkkmp.repository.page.PageRepository
import io.github.tungnk123.notionsdkkmp.repository.page.PageRepositoryImpl
import io.github.tungnk123.notionsdkkmp.repository.search.SearchRepository
import io.github.tungnk123.notionsdkkmp.repository.search.SearchRepositoryImpl
import io.github.tungnk123.notionsdkkmp.repository.user.UserRepository
import io.github.tungnk123.notionsdkkmp.repository.user.UserRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.block.BlockServiceImpl
import io.github.tungnk123.notionsdkkmp.service.database.DatabaseServiceImpl
import io.github.tungnk123.notionsdkkmp.service.datasource.DataSourceServiceImpl
import io.github.tungnk123.notionsdkkmp.service.page.PageServiceImpl
import io.github.tungnk123.notionsdkkmp.service.search.SearchServiceImpl
import io.github.tungnk123.notionsdkkmp.service.user.UserServiceImpl

internal class DefaultNotionClient(
    http: NotionHttp
) : NotionClient {
    override val pageRepository: PageRepository = PageRepositoryImpl(PageServiceImpl(http))
    override val blockRepository: BlockRepository = BlockRepositoryImpl(BlockServiceImpl(http))
    override val searchRepository: SearchRepository = SearchRepositoryImpl(SearchServiceImpl(http))
    override val userRepository: UserRepository = UserRepositoryImpl(UserServiceImpl(http))
    override val dataSourceRepository: DataSourceRepository = DataSourceRepositoryImpl(DataSourceServiceImpl(http))
    override val databaseRepository: DatabaseRepository = DatabaseRepositoryImpl(DatabaseServiceImpl(http))
}
