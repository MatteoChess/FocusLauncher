package it.univaq.focuslauncher.di

import android.content.Context
import it.univaq.focuslauncher.data.source.PreferencesDataSource
import it.univaq.focuslauncher.data.source.PackageManagerDataSource
import it.univaq.focuslauncher.data.repository.AppRepositoryImpl
import it.univaq.focuslauncher.domain.usecase.*

object AppModule {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val packageManagerDataSource by lazy {
        PackageManagerDataSource(appContext)
    }

    private val preferencesDataSource by lazy {
        PreferencesDataSource(appContext)
    }

    private val repository by lazy {
        AppRepositoryImpl(packageManagerDataSource, preferencesDataSource)
    }

    val getInstalledAppsUseCase by lazy { GetInstalledAppsUseCase(repository) }
    val getHomeAppsUseCase by lazy { GetHomeAppsUseCase(repository) }
    val getDrawerAppsUseCase by lazy { GetDrawerAppsUseCase(repository) }
    val toggleAppVisibilityUseCase by lazy { ToggleAppVisibilityUseCase(repository) }
}