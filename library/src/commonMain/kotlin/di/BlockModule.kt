package di

import org.koin.dsl.module
import repository.block.BlockRepository
import repository.block.BlockRepositoryImpl
import service.block.BlockService
import service.block.BlockServiceImpl

val blocksModule = module {
    single<BlockService> { BlockServiceImpl(get()) }
    single<BlockRepository> { BlockRepositoryImpl(get()) }
}
