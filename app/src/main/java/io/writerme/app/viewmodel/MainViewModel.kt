package io.writerme.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.writerme.app.ui.navigation.GreetingScreen
import io.writerme.app.ui.navigation.HomeScreen
import io.writerme.app.usecase.settings.GetSettingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Resolves the initial navigation route asynchronously so MainActivity never
 * has to call runBlocking (which was blocking the main thread for up to 1 s).
 *
 * null  → route not yet known; keep showing just the background
 * non-null → navigate to this route and render the NavHost
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    private val _startRoute = MutableStateFlow<String?>(null)
    val startRoute: StateFlow<String?> = _startRoute

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val settings = getSettingsUseCase()
            _startRoute.value =
                if (settings?.fullName?.isNotEmpty() == true) HomeScreen.route
                else GreetingScreen.route
        }
    }
}
