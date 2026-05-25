package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.writerme.app.ui.state.SettingsState
import io.writerme.app.usecase.settings.EnsureSettingsExistUseCase
import io.writerme.app.usecase.settings.ObserveSettingsUseCase
import io.writerme.app.usecase.settings.SetCounterUseCase
import io.writerme.app.usecase.settings.SetDarkModeUseCase
import io.writerme.app.usecase.settings.SetLanguageUseCase
import io.writerme.app.usecase.settings.UpdateProfileImageUseCase
import io.writerme.app.utils.Const
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val ensureSettingsExistUseCase: EnsureSettingsExistUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase,
    private val updateProfileImageUseCase: UpdateProfileImageUseCase,
    private val setCounterUseCase: SetCounterUseCase,
) : ViewModel() {

    private val _settingsState: MutableStateFlow<SettingsState> =
        MutableStateFlow(SettingsState.empty())
    val settingsState: StateFlow<SettingsState> = _settingsState

    init {
        viewModelScope.launch {
            ensureSettingsExistUseCase()
        }

        observeSettingsUseCase()
            .onEach { state ->
                _settingsState.emit(state.copy(languages = Const.SUPPORTED_LANGUAGES))
            }
            .launchIn(viewModelScope)
    }

    fun onLanguageChange(language: String) {
        if (language in Const.SUPPORTED_LANGUAGES) {
            viewModelScope.launch {
                setLanguageUseCase(language)
            }
        }
    }

    fun onDarkModeChange(isDarkMode: Boolean) {
        viewModelScope.launch {
            setDarkModeUseCase(isDarkMode)
        }
    }

    fun updateProfileImage(url: String) {
        viewModelScope.launch {
            updateProfileImageUseCase(url)
        }
    }

    fun onCounterChange(key: String, increase: Boolean) {
        viewModelScope.launch {
            var value = 1

            when (key) {
                Const.TEXT_CHANGES_HISTORY_KEY -> {
                    val current = _settingsState.value.textChanges
                    value = current
                    if (increase) { if (current < Const.TEXT_CHANGES_HISTORY) value = current + 1 }
                    else { if (current > 1) value = current - 1 }
                }
                Const.MEDIA_CHANGES_HISTORY_KEY -> {
                    val current = _settingsState.value.mediaChanges
                    value = current
                    if (increase) { if (current < Const.MEDIA_CHANGES_HISTORY) value = current + 1 }
                    else { if (current > 1) value = current - 1 }
                }
                Const.VOICE_CHANGES_HISTORY_KEY -> {
                    val current = _settingsState.value.voiceChanges
                    value = current
                    if (increase) { if (current < Const.VOICE_CHANGES_HISTORY) value = current + 1 }
                    else { if (current > 1) value = current - 1 }
                }
                Const.TASK_CHANGES_HISTORY_KEY -> {
                    val current = _settingsState.value.taskChanges
                    value = current
                    if (increase) { if (current < Const.TASK_CHANGES_HISTORY) value = current + 1 }
                    else { if (current > 1) value = current - 1 }
                }
                Const.LINK_CHANGES_HISTORY_KEY -> {
                    val current = _settingsState.value.linkChanges
                    value = current
                    if (increase) { if (current < Const.LINK_CHANGES_HISTORY) value = current + 1 }
                    else { if (current > 1) value = current - 1 }
                }
            }

            setCounterUseCase(key, value)
        }
    }
}
