package io.writerme.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.writerme.core.contracts.utils.MetaTagScraper
import io.writerme.network.utils.MetaTagScraperImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesMetaScraper(): MetaTagScraper {
        return MetaTagScraperImpl()
    }
}
