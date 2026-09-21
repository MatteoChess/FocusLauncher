package it.univaq.focuslauncher.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import it.univaq.focuslauncher.data.model.AppInfo
import it.univaq.focuslauncher.databinding.ActivityDrawerBinding
import kotlinx.coroutines.launch

class DrawerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDrawerBinding
    private val viewModel: DrawerViewModel by viewModels()
    private lateinit var adapter: IconAdapter
    private var allApps: List<AppInfo> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrawerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeApps()
        setupSearch()
    }

    private fun setupRecyclerView() {
        adapter = IconAdapter { app ->
            val intent = packageManager.getLaunchIntentForPackage(app.nome_package)
            intent?.let { startActivity(it) }
        }

        binding.rvDrawerApps.apply {
            layoutManager = GridLayoutManager(this@DrawerActivity, 4)
            adapter = this@DrawerActivity.adapter
        }
    }

    private fun observeApps() {
        lifecycleScope.launch {
            viewModel.drawerApps.collect { apps ->
                allApps = apps
                adapter.submitList(apps)
            }
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(
            object : androidx.appcompat.widget.SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?) = false

                override fun onQueryTextChange(newText: String?): Boolean {
                    // filtra la lista in base al testo cercato
                    val filtered = if (newText.isNullOrBlank()) {
                        allApps
                    } else {
                        allApps.filter {
                            it.label.contains(newText, ignoreCase = true)
                        }
                    }
                    adapter.submitList(filtered)
                    return true
                }
            }
        )
    }
}