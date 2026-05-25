package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.writerme.app.data.repository.SettingsRepository
import io.writerme.app.ui.state.SettingsState
import io.writerme.app.utils.Const
import io.writerme.app.utils.FilesUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val filesUtil: FilesUtil,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _settingsState: MutableStateFlow<SettingsState> =
        MutableStateFlow(SettingsState.empty())
    val settingsState: StateFlow<SettingsState> = _settingsState

    init {
        viewModelScope.launch {
            settingsRepository.ensureSettingsExist()
        }

        settingsRepository.observeSettings()
            .onEach { state ->
                _settingsState.emit(state.copy(languages = Const.SUPPORTED_LANGUAGES))
            }
            .launchIn(viewModelScope)
    }

    fun onLanguageChange(language: String) {
        if (language in Const.SUPPORTED_LANGUAGES) {
            viewModelScope.launch {
                settingsRepository.setLanguage(language)
            }
        }
    }

    fun onDarkModeChange(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingsRepository.setDarkMode(isDarkMode)
        }
    }

    fun updateProfileImage(url: String) {
        viewModelScope.launch {
            val uri = filesUtil.writeImageToFile(url)
            uri?.let { settingsRepository.updateProfileImage(it) }
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

            settingsRepository.setCounter(key, value)
        }
    }
}
