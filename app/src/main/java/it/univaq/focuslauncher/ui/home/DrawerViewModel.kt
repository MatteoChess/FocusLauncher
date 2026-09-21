package it.univaq.focuslauncher.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.univaq.focuslauncher.data.model.AppInfo
import it.univaq.focuslauncher.di.AppModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DrawerViewModel : ViewModel() {

    private val getInstalledApps = AppModule.getInstalledAppsUseCase
    private val getDrawerApps = AppModule.getDrawerAppsUseCase

    private val _drawerApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val drawerApps: StateFlow<List<AppInfo>> = _drawerApps

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadDrawerApps()
    }

    private fun loadDrawerApps() {
        viewModelScope.launch {
            _isLoading.value = true

            val allApps = getInstalledApps()

            getDrawerApps(allApps).collect { apps ->
                _drawerApps.value = apps
                _isLoading.value = false
            }
        }
    }
}