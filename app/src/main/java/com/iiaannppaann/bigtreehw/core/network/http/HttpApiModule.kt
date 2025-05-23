package com.iiaannppaann.bigtreehw.core.network.http

import android.content.Context
import com.iiaannppaann.bigtreehw.R
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object HttpApiModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        @ApplicationContext context: Context,
        moshi: Moshi,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(context.getString(R.string.base_url))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
}
