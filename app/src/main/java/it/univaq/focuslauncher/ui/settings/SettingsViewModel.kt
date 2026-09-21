package it.univaq.focuslauncher.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.univaq.focuslauncher.data.model.AppInfo
import it.univaq.focuslauncher.di.AppModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {

    private val getInstalledApps = AppModule.getInstalledAppsUseCase
    private val toggleAppVisibility = AppModule.toggleAppVisibilityUseCase
    private val repository = AppModule.getHomeAppsUseCase

    // lista completa con flag "è in home?"
    private val _appsWithState = MutableStateFlow<List<Pair<AppInfo, Boolean>>>(emptyList())
    val appsWithState: StateFlow<List<Pair<AppInfo, Boolean>>> = _appsWithState

    private val _currentHomeApps = MutableStateFlow<Set<String>>(emptySet())

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            val allApps = getInstalledApps()

            // combina lista app + set home per sapere quali sono spuntate
            combine(
                flow { emit(allApps) },
                AppModule.getHomeAppsUseCase(allApps)
            ) { apps, homeApps ->
                _currentHomeApps.value = homeApps.map { it.nome_package }.toSet()
                apps.map { app ->
                    app to (app.nome_package in _currentHomeApps.value)
                }
            }.collect { appsWithState ->
                _appsWithState.value = appsWithState
            }
        }
    }

    // chiamato quando utente tocca un'app nelle impostazioni
    fun toggleApp(packageName: String) {
        viewModelScope.launch {
            toggleAppVisibility(packageName, _currentHomeApps.value)
        }
    }
}