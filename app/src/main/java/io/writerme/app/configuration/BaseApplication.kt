package io.writerme.app.configuration

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.writerme.core.contracts.datasources.local.SettingsLocalDataSource
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {

    @Inject
    private var settingsLocalDataSource: SettingsLocalDataSource? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
        settingsLocalDataSource?.close()
    }
}
