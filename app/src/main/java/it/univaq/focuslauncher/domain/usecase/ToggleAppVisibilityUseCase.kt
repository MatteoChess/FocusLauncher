package it.univaq.focuslauncher.domain.usecase

import it.univaq.focuslauncher.domain.repository.AppRepository

class ToggleAppVisibilityUseCase (private val repository: AppRepository) {
    suspend operator fun invoke(packageName: String, current: Set<String>): Set<String> =
        repository.toggleHomeApp(packageName, current)
}