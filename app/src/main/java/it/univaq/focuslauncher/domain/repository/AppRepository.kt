package it.univaq.focuslauncher.domain.repository

import it.univaq.focuslauncher.data.model.AppInfo
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getAllApps(): List<AppInfo>
    fun getHomeAppPackages(): Flow<Set<String>>
    suspend fun toggleHomeApp(packageName: String, setCorrente: Set<String>): Set<String>
}