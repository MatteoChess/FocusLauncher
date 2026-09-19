package it.univaq.focuslauncher.data.source

import android.content.Context
import android.content.pm.PackageManager
import it.univaq.focuslauncher.data.model.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PackageManagerDataSource (private val context : Context) {
    /**
     * Il metodo recupera tutte le app installate sul telefono che abbiano
     * un `Intent` associatto non nullo, ed esclude se stesso dal ciclo.
     * Poi mappa ogni app trovata nel modello di riferimento `AppInfo`
     */
    suspend fun getInstalledApp() : List<AppInfo> = withContext(Dispatchers.IO){
        val pm = context.packageManager
        pm.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter{
                pm.getLaunchIntentForPackage(it.packageName) != null && it.packageName != context.packageName
            }
            .map{
                AppInfo(
                    nome_package = it.packageName,
                    label = pm.getApplicationLabel(it).toString(),
                    icona = pm.getApplicationIcon(it.packageName)
                )
            }
            .sortedBy{
                it.label
            }
    }
}