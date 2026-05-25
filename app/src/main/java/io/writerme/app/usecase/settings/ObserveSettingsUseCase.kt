package io.writerme.app.usecase.settings

import io.writerme.app.data.repository.SettingsRepository
import io.writerme.app.ui.state.SettingsState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveSettingsUseCase {
    operator fun invoke(): Flow<SettingsState>
}

class ObserveSettingsUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ObserveSettingsUseCase {
    override fun invoke(): Flow<SettingsState> = settingsRepository.observeSettings()
}
