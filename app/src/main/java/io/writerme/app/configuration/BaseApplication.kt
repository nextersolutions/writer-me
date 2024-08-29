package io.writerme.app.configuration

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.writerme.core.contracts.datasources.local.BookmarksLocalDataSource
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {

    @Inject
    var bookmarksLocalDataSource: BookmarksLocalDataSource? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
        bookmarksLocalDataSource?.close()
    }
}
