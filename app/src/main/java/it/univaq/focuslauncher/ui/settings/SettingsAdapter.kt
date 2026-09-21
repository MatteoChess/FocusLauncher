package it.univaq.focuslauncher.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.univaq.focuslauncher.data.model.AppInfo
import it.univaq.focuslauncher.databinding.ItemAppSettingsBinding

class SettingsAdapter(
    private val onToggle: (String, Boolean) -> Unit
) : ListAdapter<Pair<AppInfo, Boolean>, SettingsAdapter.SettingsViewHolder>(SettingsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val binding = ItemAppSettingsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SettingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SettingsViewHolder(
        private val binding: ItemAppSettingsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pair<AppInfo, Boolean>) {
            val (app, isInHome) = item

            binding.ivAppIcon.setImageDrawable(app.icona)
            binding.tvAppName.text = app.label

            // evita loop di eventi mentre si aggiorna la UI
            binding.switchHomeApp.setOnCheckedChangeListener(null)
            binding.switchHomeApp.isChecked = isInHome
            binding.switchHomeApp.setOnCheckedChangeListener { _, isChecked ->
                onToggle(app.nome_package, isChecked)
            }
        }
    }

    class SettingsDiffCallback : DiffUtil.ItemCallback<Pair<AppInfo, Boolean>>() {
        override fun areItemsTheSame(
            old: Pair<AppInfo, Boolean>,
            new: Pair<AppInfo, Boolean>
        ) = old.first.nome_package == new.first.nome_package

        override fun areContentsTheSame(
            old: Pair<AppInfo, Boolean>,
            new: Pair<AppInfo, Boolean>
        ) = old.second == new.second
    }
}