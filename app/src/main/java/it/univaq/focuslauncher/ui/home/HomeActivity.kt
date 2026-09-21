package it.univaq.focuslauncher.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import it.univaq.focuslauncher.databinding.ActivityHomeBinding
import it.univaq.focuslauncher.ui.settings.SettingsActivity
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: IconAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeApps()
        setupButtons()
    }

    private fun setupRecyclerView() {
        adapter = IconAdapter { app ->
            // lancia l'app toccata
            val intent = packageManager.getLaunchIntentForPackage(app.nome_package)
            intent?.let { startActivity(it) }
        }

        binding.rvHomeApps.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, 4)
            adapter = this@HomeActivity.adapter
        }
    }

    private fun observeApps() {
        lifecycleScope.launch {
            viewModel.homeApps.collect { apps ->
                adapter.submitList(apps)
            }
        }
    }

    private fun setupButtons() {
        binding.fabOpenDrawer.setOnClickListener {
            startActivity(Intent(this, DrawerActivity::class.java))
        }
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}