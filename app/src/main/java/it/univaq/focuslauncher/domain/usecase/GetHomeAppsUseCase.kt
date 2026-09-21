package it.univaq.focuslauncher.domain.usecase

import it.univaq.focuslauncher.data.model.AppInfo
import it.univaq.focuslauncher.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class GetHomeAppsUseCase(private val repository: AppRepository) {

    // restituisce solo le app che l'utente ha messo in home
    operator fun invoke(allApps: List<AppInfo>): Flow<List<AppInfo>> =
        repository.getHomeAppPackages().let { homePackagesFlow ->
            combine(
                flow { emit(allApps) },
                homePackagesFlow
            ) { apps, homePackages ->
                apps.filter { it.nome_package in homePackages }
            }
        }
}