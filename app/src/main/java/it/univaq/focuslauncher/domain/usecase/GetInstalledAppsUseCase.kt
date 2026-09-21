package it.univaq.focuslauncher.domain.usecase

import it.univaq.focuslauncher.data.model.AppInfo
import it.univaq.focuslauncher.domain.repository.AppRepository

class GetInstalledAppsUseCase(private val repository: AppRepository) {
    suspend operator fun invoke(): List<AppInfo> =
        repository.getAllApps()
}