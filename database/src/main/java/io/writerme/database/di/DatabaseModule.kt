package io.writerme.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.writerme.core.contracts.datasources.local.BookmarksLocalDataSource
import io.writerme.database.extensions.getDefaultInstance
import io.writerme.database.localdatasource.BookmarksLocalDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {

    @Provides
    @Singleton
    fun provideRealmInstance(): Realm {
        return Realm.getDefaultInstance()
    }

    @Provides
    fun providesBookmarksLocalDataSource(
        realm: Realm
    ): BookmarksLocalDataSource {
        return BookmarksLocalDataSourceImpl(realm)
    }
}