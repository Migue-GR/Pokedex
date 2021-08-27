package com.pokedex.view.evolutions

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pokedex.R
import com.pokedex.databinding.FragmentEvolutionsBinding
import com.pokedex.model.enums.PokeError
import com.pokedex.model.enums.PokeError.FAIL_POKEMON_FAVORITE
import com.pokedex.model.enums.Success
import com.pokedex.model.enums.Success.SUCCESS_POKEMON_FAVORITE
import com.pokedex.model.local.Pokemon
import com.pokedex.utils.BaseFragmentBinding
import com.pokedex.utils.UiEventsManager.error
import com.pokedex.utils.UiEventsManager.shouldShowLocalPokes
import com.pokedex.utils.UiEventsManager.showError
import com.pokedex.utils.UiEventsManager.showSuccess
import com.pokedex.utils.UiEventsManager.success
import com.pokedex.view.adapters.EvolutionsAdapter
import com.pokedex.viewmodel.EvolutionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EvolutionsFragment : BaseFragmentBinding<FragmentEvolutionsBinding>() {
    override fun bind() = FragmentEvolutionsBinding.inflate(layoutInflater)

    private val evolutionViewModel: EvolutionViewModel by viewModel()
    private lateinit var pokemon: Pokemon

    override fun onViewCreated() {
        pokemon = EvolutionsFragmentArgs.fromBundle(requireArguments()).pokemon
        setObservers()
        evolutionViewModel.getEvolutionChain(pokemon.evolutionChainUrl)
    }

    private fun setObservers() {
        evolutionViewModel.evolutionChain.observe(viewLifecycleOwner, { evolutions ->
            binding.rcvEvolutions.layoutManager = LinearLayoutManager(requireContext())
            binding.rcvEvolutions.adapter = EvolutionsAdapter(evolutions, ::onEvolutionClicked)
        })
        error.observe(viewLifecycleOwner, { error ->
            if (error == FAIL_POKEMON_FAVORITE) {
                navigateToPokeListFragment()
                showError(PokeError.UNKNOWN)
            }
        })
        success.observe(viewLifecycleOwner, { success ->
            if (success == SUCCESS_POKEMON_FAVORITE) {
                navigateToPokeListFragment()
                showSuccess(Success.UNKNOWN)
            }
        })
    }

    private fun navigateToPokeListFragment() {
        shouldShowLocalPokes = true
        findNavController().popBackStack(R.id.pokeListFragment, false)
    }

    private fun onEvolutionClicked(name: String) {
        evolutionViewModel.markAsFavorite(name)
    }
}