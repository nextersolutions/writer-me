package io.writerme.app.di

import android.app.Application
import io.writerme.core.contracts.dispatchers.CoroutineDispatcher
import io.writerme.core.contracts.dispatchers.IConnectionDetector
import io.writerme.core.contracts.dispatchers.ICoroutineDispatcher
import io.writerme.core.contracts.validators.INetworkConnectivityService
import io.writerme.core.network.ConnectionDetector
import io.writerme.core.network.NetworkConnectivityService
import io.writerme.core.preferences.AppPrefManager
import io.writerme.resources.resources.ResourcesProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun provideCoroutineDispatchers(): ICoroutineDispatcher {
        return CoroutineDispatcher()
    }

    @Provides
    fun provideIConnectionDetector(
        application: Application,
        coroutineDispatcher: ICoroutineDispatcher
    ): IConnectionDetector {
        return ConnectionDetector(application, coroutineDispatcher)
    }

    @Provides
    fun provideINetworkConnectivityService(
        dispatcher: ICoroutineDispatcher,
        application: Application
    ): INetworkConnectivityService {
        return NetworkConnectivityService(dispatcher, application)
    }

    @Provides
    fun provideAppPrefManager(
        application: Application
    ): AppPrefManager {
        return AppPrefManager(application)
    }

    @Singleton
    @Provides
    fun provideResources(application: Application): ResourcesProvider {
        return ResourcesProvider(application)
    }
}
