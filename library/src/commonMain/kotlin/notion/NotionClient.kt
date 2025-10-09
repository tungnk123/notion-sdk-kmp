package notion

import repository.block.BlockRepository
import repository.datasource.DataSourceRepository
import repository.page.PageRepository
import repository.search.SearchRepository
import repository.user.UserRepository

interface NotionClient {
    val pages: PageRepository
    val blocks: BlockRepository
    val search: SearchRepository
    val users: UserRepository
    val dataSources: DataSourceRepository
}
