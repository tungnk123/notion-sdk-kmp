package io.github.tungnk123.notionsdkkmp.di

import org.koin.dsl.module
import io.github.tungnk123.notionsdkkmp.repository.block.BlockRepository
import io.github.tungnk123.notionsdkkmp.repository.block.BlockRepositoryImpl
import io.github.tungnk123.notionsdkkmp.service.block.BlockService
import io.github.tungnk123.notionsdkkmp.service.block.BlockServiceImpl

val blocksModule = module {
    single<BlockService> { BlockServiceImpl(get()) }
    single<BlockRepository> { BlockRepositoryImpl(get()) }
}
