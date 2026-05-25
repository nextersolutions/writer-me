package io.writerme.app.usecase.settings

import io.writerme.app.data.repository.SettingsRepository
import javax.inject.Inject

interface SetCounterUseCase {
    suspend operator fun invoke(key: String, value: Int)
}

class SetCounterUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository
) : SetCounterUseCase {
    override suspend fun invoke(key: String, value: Int) = settingsRepository.setCounter(key, value)
}
