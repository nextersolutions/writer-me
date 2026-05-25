package io.writerme.app.usecase.settings

import io.writerme.app.data.model.SettingsEntity
import io.writerme.app.data.repository.SettingsRepository
import javax.inject.Inject

interface GetSettingsUseCase {
    suspend operator fun invoke(): SettingsEntity?
}

class GetSettingsUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository
) : GetSettingsUseCase {
    override suspend fun invoke(): SettingsEntity? = settingsRepository.getSettings()
}
