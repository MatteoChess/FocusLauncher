package it.univaq.focuslauncher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.univaq.focuslauncher.data.model.AppInfo
import it.univaq.focuslauncher.di.AppModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val getInstalledApps = AppModule.getInstalledAppsUseCase
    private val getHomeApps = AppModule.getHomeAppsUseCase

    // stato della UI
    private val _homeApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val homeApps: StateFlow<List<AppInfo>> = _homeApps

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadHomeApps()
    }

    private fun loadHomeApps() {
        viewModelScope.launch {
            _isLoading.value = true

            // 1. prende tutte le app installate
            val allApps = getInstalledApps()

            // 2. filtra solo quelle in home
            getHomeApps(allApps).collect { apps ->
                _homeApps.value = apps
                _isLoading.value = false
            }
        }
    }
}