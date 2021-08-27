package com.pokedex.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pokedex.databinding.ItemEvolutionBinding
import com.pokedex.utils.extensions.setOnSingleClickListener

class EvolutionsAdapter(
    private val items: List<String>,
    private val onItemClicked: (String) -> Unit
) :
    RecyclerView.Adapter<EvolutionsViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: EvolutionsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionsViewHolder {
        return EvolutionsViewHolder(
            ItemEvolutionBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClicked
        )
    }
}

class EvolutionsViewHolder(
    private val binding: ItemEvolutionBinding,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: String) {
        binding.tvName.text = item
        binding.cardItem.setOnSingleClickListener { onItemClicked(item) }
    }
}