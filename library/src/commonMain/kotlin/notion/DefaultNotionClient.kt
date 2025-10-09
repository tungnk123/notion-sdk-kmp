package notion

import http.NotionHttp
import repository.block.BlockRepository
import repository.block.BlockRepositoryImpl
import repository.datasource.DataSourceRepository
import repository.datasource.DataSourceRepositoryImpl
import repository.page.PageRepository
import repository.page.PageRepositoryImpl
import repository.search.SearchRepository
import repository.search.SearchRepositoryImpl
import repository.user.UserRepository
import repository.user.UserRepositoryImpl
import service.block.BlockServiceImpl
import service.datasource.DataSourceServiceImpl
import service.page.PageServiceImpl
import service.search.SearchServiceImpl
import service.user.UserServiceImpl

internal class DefaultNotionClient(
    http: NotionHttp
) : NotionClient {
    override val pages: PageRepository = PageRepositoryImpl(PageServiceImpl(http))
    override val blocks: BlockRepository = BlockRepositoryImpl(BlockServiceImpl(http))
    override val search: SearchRepository = SearchRepositoryImpl(SearchServiceImpl(http))
    override val users: UserRepository = UserRepositoryImpl(UserServiceImpl(http))
    override val dataSources: DataSourceRepository = DataSourceRepositoryImpl(DataSourceServiceImpl(http))
}
