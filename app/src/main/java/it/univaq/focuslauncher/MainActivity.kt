package it.univaq.focuslauncher

import android.app.Application
import it.univaq.focuslauncher.di.AppModule

class MainActivity : Application() {
    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
    }
}