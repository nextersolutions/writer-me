package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.writerme.app.data.repository.SettingsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    fun saveName(name: String) {
        viewModelScope.launch {
            settingsRepository.saveName(name)
        }
    }
}
