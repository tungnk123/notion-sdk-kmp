package notion

import http.NotionHttp
import repository.block.BlockRepository
import repository.block.BlockRepositoryImpl
import repository.database.DatabaseRepository
import repository.database.DatabaseRepositoryImpl
import repository.datasource.DataSourceRepository
import repository.datasource.DataSourceRepositoryImpl
import repository.page.PageRepository
import repository.page.PageRepositoryImpl
import repository.search.SearchRepository
import repository.search.SearchRepositoryImpl
import repository.user.UserRepository
import repository.user.UserRepositoryImpl
import service.block.BlockServiceImpl
import service.database.DatabaseServiceImpl
import service.datasource.DataSourceServiceImpl
import service.page.PageServiceImpl
import service.search.SearchServiceImpl
import service.user.UserServiceImpl

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
