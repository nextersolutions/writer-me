package io.writerme.app.usecase.settings

import io.writerme.app.data.repository.SettingsRepository
import javax.inject.Inject

interface SetDarkModeUseCase {
    suspend operator fun invoke(isDarkMode: Boolean)
}

class SetDarkModeUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository
) : SetDarkModeUseCase {
    override suspend fun invoke(isDarkMode: Boolean) = settingsRepository.setDarkMode(isDarkMode)
}
