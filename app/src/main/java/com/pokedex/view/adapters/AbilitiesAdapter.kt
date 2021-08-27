package com.pokedex.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pokedex.databinding.ItemPokeBinding
import com.pokedex.model.local.Ability

class AbilitiesAdapter(private val items: List<Ability>) :
    RecyclerView.Adapter<AbilitiesViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: AbilitiesViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilitiesViewHolder {
        return AbilitiesViewHolder(
            ItemPokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}

class AbilitiesViewHolder(
    private val binding: ItemPokeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Ability) {
        binding.tvName.text = item.name
    }
}