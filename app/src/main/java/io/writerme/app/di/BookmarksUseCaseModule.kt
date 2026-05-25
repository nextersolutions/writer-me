package io.writerme.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.writerme.app.usecase.bookmarks.CreateBookmarkUseCase
import io.writerme.app.usecase.bookmarks.CreateBookmarkUseCaseImpl
import io.writerme.app.usecase.bookmarks.CreateFolderUseCase
import io.writerme.app.usecase.bookmarks.CreateFolderUseCaseImpl
import io.writerme.app.usecase.bookmarks.DeleteBookmarkUseCase
import io.writerme.app.usecase.bookmarks.DeleteBookmarkUseCaseImpl
import io.writerme.app.usecase.bookmarks.DeleteFolderUseCase
import io.writerme.app.usecase.bookmarks.DeleteFolderUseCaseImpl
import io.writerme.app.usecase.bookmarks.EnsureRootFolderUseCase
import io.writerme.app.usecase.bookmarks.EnsureRootFolderUseCaseImpl
import io.writerme.app.usecase.bookmarks.ObserveFolderUseCase
import io.writerme.app.usecase.bookmarks.ObserveFolderUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class BookmarksUseCaseModule {

    @Binds abstract fun bindEnsureRootFolder(impl: EnsureRootFolderUseCaseImpl): EnsureRootFolderUseCase
    @Binds abstract fun bindObserveFolder(impl: ObserveFolderUseCaseImpl): ObserveFolderUseCase
    @Binds abstract fun bindCreateFolder(impl: CreateFolderUseCaseImpl): CreateFolderUseCase
    @Binds abstract fun bindCreateBookmark(impl: CreateBookmarkUseCaseImpl): CreateBookmarkUseCase
    @Binds abstract fun bindDeleteFolder(impl: DeleteFolderUseCaseImpl): DeleteFolderUseCase
    @Binds abstract fun bindDeleteBookmark(impl: DeleteBookmarkUseCaseImpl): DeleteBookmarkUseCase
}
