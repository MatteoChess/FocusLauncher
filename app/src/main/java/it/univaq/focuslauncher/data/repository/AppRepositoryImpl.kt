package it.univaq.focuslauncher.data.repository

import it.univaq.focuslauncher.data.model.AppInfo
import it.univaq.focuslauncher.data.source.PackageManagerDataSource
import it.univaq.focuslauncher.data.source.PreferencesDataSource
import it.univaq.focuslauncher.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl (
    private val pmSource: PackageManagerDataSource,
    private val prefSource: PreferencesDataSource
) : AppRepository {
    override suspend fun getAllApps() : List<AppInfo>{
        return pmSource.getInstalledApp()
    }

    override fun getHomeAppPackages(): Flow<Set<String>> {
        return prefSource.apps_home_flow
    }

    override suspend fun toggleHomeApp(packageName: String, setCorrente: Set<String>): Set<String>{
        return prefSource.toggleApp(packageName, setCorrente)
    }

}