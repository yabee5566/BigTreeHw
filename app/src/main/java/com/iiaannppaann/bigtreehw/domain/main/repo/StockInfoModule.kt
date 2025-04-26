package com.iiaannppaann.bigtreehw.domain.main.repo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StockInfoModule {
    @Binds
    @Singleton
    abstract fun bindStockInfoRepo(impl: StockInfoRepoImpl): StockInfoRepo
}
