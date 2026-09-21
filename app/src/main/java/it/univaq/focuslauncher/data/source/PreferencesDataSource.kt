package it.univaq.focuslauncher.data.source

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("launcher_prefs_apps")

class PreferencesDataSource (private val context: Context) {

    companion object{
        private val APP_HOME_KEY = stringSetPreferencesKey("app_home")
    }

    val apps_home_flow: Flow<Set<String>> = context.dataStore.data.map{ prefs ->
        prefs[APP_HOME_KEY] ?: emptySet()
    }

    suspend  fun setAppHome(packageNames: Set<String>){
        context.dataStore.edit{ prefs ->
            prefs[APP_HOME_KEY] = packageNames
        }
    }

    // se è già nella lista lo toglie altrimenti lo aggiunge
    suspend fun toggleApp(packageName: String, setCorrente: Set<String>): Set<String>{
        val setAggiornato = if(packageName in setCorrente){
            setCorrente - packageName
        } else {
            setCorrente + packageName
        }
        setAppHome(setAggiornato)
        return setAggiornato
    }
}