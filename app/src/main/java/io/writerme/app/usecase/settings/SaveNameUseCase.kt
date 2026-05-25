package io.writerme.app.usecase.settings

import io.writerme.app.data.repository.SettingsRepository
import javax.inject.Inject

interface SaveNameUseCase {
    suspend operator fun invoke(name: String)
}

class SaveNameUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository
) : SaveNameUseCase {
    override suspend fun invoke(name: String) = settingsRepository.saveName(name)
}
