package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.writerme.app.usecase.settings.SaveNameUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val saveNameUseCase: SaveNameUseCase
) : ViewModel() {

    fun saveName(name: String) {
        viewModelScope.launch {
            saveNameUseCase(name)
        }
    }
}
