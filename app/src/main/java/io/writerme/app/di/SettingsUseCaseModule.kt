package io.writerme.app.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.writerme.app.usecase.settings.EnsureSettingsExistUseCase
import io.writerme.app.usecase.settings.EnsureSettingsExistUseCaseImpl
import io.writerme.app.usecase.settings.GetSettingsUseCase
import io.writerme.app.usecase.settings.GetSettingsUseCaseImpl
import io.writerme.app.usecase.settings.ObserveSettingsUseCase
import io.writerme.app.usecase.settings.ObserveSettingsUseCaseImpl
import io.writerme.app.usecase.settings.SaveNameUseCase
import io.writerme.app.usecase.settings.SaveNameUseCaseImpl
import io.writerme.app.usecase.settings.SetCounterUseCase
import io.writerme.app.usecase.settings.SetCounterUseCaseImpl
import io.writerme.app.usecase.settings.SetDarkModeUseCase
import io.writerme.app.usecase.settings.SetDarkModeUseCaseImpl
import io.writerme.app.usecase.settings.SetLanguageUseCase
import io.writerme.app.usecase.settings.SetLanguageUseCaseImpl
import io.writerme.app.usecase.settings.UpdateProfileImageUseCase
import io.writerme.app.usecase.settings.UpdateProfileImageUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsUseCaseModule {

    @Binds abstract fun bindObserveSettings(impl: ObserveSettingsUseCaseImpl): ObserveSettingsUseCase
    @Binds abstract fun bindEnsureSettingsExist(impl: EnsureSettingsExistUseCaseImpl): EnsureSettingsExistUseCase
    @Binds abstract fun bindSaveName(impl: SaveNameUseCaseImpl): SaveNameUseCase
    @Binds abstract fun bindSetLanguage(impl: SetLanguageUseCaseImpl): SetLanguageUseCase
    @Binds abstract fun bindSetDarkMode(impl: SetDarkModeUseCaseImpl): SetDarkModeUseCase
    @Binds abstract fun bindUpdateProfileImage(impl: UpdateProfileImageUseCaseImpl): UpdateProfileImageUseCase
    @Binds abstract fun bindSetCounter(impl: SetCounterUseCaseImpl): SetCounterUseCase
    @Binds abstract fun bindGetSettings(impl: GetSettingsUseCaseImpl): GetSettingsUseCase
}
