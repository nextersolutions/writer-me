package io.writerme.app.usecase.settings

import io.writerme.app.data.repository.SettingsRepository
import javax.inject.Inject

interface SetLanguageUseCase {
    suspend operator fun invoke(language: String)
}

class SetLanguageUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository
) : SetLanguageUseCase {
    override suspend fun invoke(language: String) = settingsRepository.setLanguage(language)
}
