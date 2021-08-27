package com.pokedex.view.abilities

import androidx.recyclerview.widget.LinearLayoutManager
import com.pokedex.databinding.FragmentAbilitiesBinding
import com.pokedex.model.local.Pokemon
import com.pokedex.utils.BaseFragmentBinding
import com.pokedex.view.adapters.AbilitiesAdapter
import com.pokedex.view.poke.PokeFragmentArgs
import com.pokedex.viewmodel.AbilityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AbilitiesFragment : BaseFragmentBinding<FragmentAbilitiesBinding>() {
    override fun bind() = FragmentAbilitiesBinding.inflate(layoutInflater)

    private val abilityViewModel: AbilityViewModel by viewModel()
    private lateinit var pokemon: Pokemon

    override fun onViewCreated() {
        pokemon = PokeFragmentArgs.fromBundle(requireArguments()).pokemon
        setObservers()
        abilityViewModel.getAbilitiesByName(pokemon.name)
    }

    private fun setObservers() {
        abilityViewModel.abilities.observe(viewLifecycleOwner, { abilities ->
            binding.rcvAbilities.layoutManager = LinearLayoutManager(requireContext())
            binding.rcvAbilities.adapter = AbilitiesAdapter(abilities)
        })
    }
}