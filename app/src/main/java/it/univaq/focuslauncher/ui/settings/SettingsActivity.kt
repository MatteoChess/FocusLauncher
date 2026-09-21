package it.univaq.focuslauncher.ui.settings

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import it.univaq.focuslauncher.databinding.ActivitySettingsBinding
import it.univaq.focuslauncher.ui.settings.SettingsViewModel
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModels()
    private lateinit var adapter: SettingsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeApps()
    }

    private fun setupRecyclerView() {
        adapter = SettingsAdapter { packageName, _ ->
            // toggle dell'app in home
            viewModel.toggleApp(packageName)
        }

        binding.rvSettingsApps.apply {
            layoutManager = LinearLayoutManager(this@SettingsActivity)
            adapter = this@SettingsActivity.adapter
        }
    }

    private fun observeApps() {
        lifecycleScope.launch {
            viewModel.appsWithState.collect { apps ->
                adapter.submitList(apps)
            }
        }
    }
}