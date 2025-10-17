package io.github.tungnk123.notionsdkkmp.notion

import repository.block.BlockRepository
import repository.database.DatabaseRepository
import repository.datasource.DataSourceRepository
import repository.page.PageRepository
import repository.search.SearchRepository
import repository.user.UserRepository

interface NotionClient {
    val pageRepository: PageRepository
    val blockRepository: BlockRepository
    val searchRepository: SearchRepository
    val userRepository: UserRepository
    val dataSourceRepository: DataSourceRepository
    val databaseRepository: DatabaseRepository
}
