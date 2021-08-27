package com.pokedex.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pokedex.databinding.ItemPokeBinding
import com.pokedex.model.local.Pokemon
import com.pokedex.utils.extensions.setOnSingleClickListener

class PokeAdapter(
    private val items: List<Pokemon>,
    private val onItemClicked: (Pokemon) -> Unit
) : RecyclerView.Adapter<PokeViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        return PokeViewHolder(
            ItemPokeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onItemClicked
        )
    }
}

class PokeViewHolder(
    private val binding: ItemPokeBinding,
    private val onItemClicked: (Pokemon) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Pokemon) {
        binding.tvName.text = if (item.prefix.isEmpty()) {
            item.name
        } else {
            item.prefix + item.name
        }
        binding.cardItem.setOnSingleClickListener { onItemClicked(item) }
    }
}