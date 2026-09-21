package it.univaq.focuslauncher.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.univaq.focuslauncher.data.model.AppInfo
import it.univaq.focuslauncher.databinding.ItemAppBinding

class IconAdapter(
    private val onAppClick: (AppInfo) -> Unit
) : ListAdapter<AppInfo, IconAdapter.AppViewHolder>(AppDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = ItemAppBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AppViewHolder(
        private val binding: ItemAppBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(app: AppInfo) {
            binding.ivAppIcon.setImageDrawable(app.icona)
            binding.tvAppName.text = app.label
            binding.root.setOnClickListener { onAppClick(app) }
        }
    }

    class AppDiffCallback : DiffUtil.ItemCallback<AppInfo>() {
        override fun areItemsTheSame(old: AppInfo, new: AppInfo) =
            old.nome_package == new.nome_package
        override fun areContentsTheSame(old: AppInfo, new: AppInfo) =
            old.label == new.label
    }
}