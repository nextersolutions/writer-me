package io.writerme.app.usecase.settings

import io.writerme.app.data.repository.SettingsRepository
import javax.inject.Inject

interface EnsureSettingsExistUseCase {
    suspend operator fun invoke()
}

class EnsureSettingsExistUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository
) : EnsureSettingsExistUseCase {
    override suspend fun invoke() = settingsRepository.ensureSettingsExist()
}
