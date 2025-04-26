package com.iiaannppaann.bigtreehw.core.network.http.serialization.di

import com.iiaannppaann.bigtreehw.core.network.http.serialization.SafeFloatAdapter
import com.iiaannppaann.bigtreehw.core.network.http.serialization.SafeLongAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SerializationModule {
    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi
            .Builder()
            .add(SafeLongAdapter)
            .add(SafeFloatAdapter)
            .build()
}
