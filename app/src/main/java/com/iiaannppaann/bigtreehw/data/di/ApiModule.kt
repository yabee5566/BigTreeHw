package com.iiaannppaann.bigtreehw.data.di

import com.iiaannppaann.bigtreehw.data.api.ExchangeReportApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideExchangeReportApi(retrofit: Retrofit): ExchangeReportApi = retrofit.create(ExchangeReportApi::class.java)
}
