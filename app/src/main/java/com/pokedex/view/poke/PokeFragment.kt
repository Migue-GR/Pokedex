package com.pokedex.view.poke

import com.pokedex.databinding.FragmentPokeBinding
import com.pokedex.model.local.Pokemon
import com.pokedex.utils.BaseFragmentBinding
import com.pokedex.utils.extensions.navigateSafelyWithDirections
import com.pokedex.utils.extensions.setOnSingleClickListener
import com.pokedex.viewmodel.PokeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokeFragment : BaseFragmentBinding<FragmentPokeBinding>() {
    override fun bind() = FragmentPokeBinding.inflate(layoutInflater)

    private val pokeViewModel: PokeViewModel by viewModel()
    private lateinit var pokemon: Pokemon

    override fun onViewCreated() {
        pokemon = PokeFragmentArgs.fromBundle(requireArguments()).pokemon
        binding.tvName.text = pokemon.name
        setObservers()
        setOnClickListeners()
        pokeViewModel.getPokeByName(pokemon.name)
    }

    private fun setObservers() {
        pokeViewModel.poke.observe(viewLifecycleOwner, { poke ->
            pokemon = poke
            binding.tvBaseHappiness.text = poke.baseHappiness.toString()
            binding.tvCaptureRate.text = poke.captureRate.toString()
            binding.tvEggGroups.text = poke.eggGroups
        })
    }

    private fun setOnClickListeners() {
        binding.btnAbilities.setOnSingleClickListener {
            val directions = PokeFragmentDirections.actionPokeFragmentToAbilitiesFragment(pokemon)
            navigateSafelyWithDirections(directions)
        }
        binding.btnEvolutionChain.setOnSingleClickListener {
            val directions = PokeFragmentDirections.actionPokeFragmentToEvolutionsFragment(pokemon)
            navigateSafelyWithDirections(directions)
        }
    }
}