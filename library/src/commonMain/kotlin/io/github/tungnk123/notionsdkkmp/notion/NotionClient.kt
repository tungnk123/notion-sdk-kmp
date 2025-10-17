package io.github.tungnk123.notionsdkkmp.notion

import io.github.tungnk123.notionsdkkmp.repository.block.BlockRepository
import io.github.tungnk123.notionsdkkmp.repository.database.DatabaseRepository
import io.github.tungnk123.notionsdkkmp.repository.datasource.DataSourceRepository
import io.github.tungnk123.notionsdkkmp.repository.page.PageRepository
import io.github.tungnk123.notionsdkkmp.repository.search.SearchRepository
import io.github.tungnk123.notionsdkkmp.repository.user.UserRepository

interface NotionClient {
    val pageRepository: PageRepository
    val blockRepository: BlockRepository
    val searchRepository: SearchRepository
    val userRepository: UserRepository
    val dataSourceRepository: DataSourceRepository
    val databaseRepository: DatabaseRepository
}
