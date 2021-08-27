package com.pokedex.view.pokelist

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.pokedex.databinding.FragmentPokeListBinding
import com.pokedex.model.local.Pokemon
import com.pokedex.utils.BaseFragmentBinding
import com.pokedex.utils.UiEventsManager.shouldShowLocalPokes
import com.pokedex.utils.UiEventsManager.shouldShowRemotePokes
import com.pokedex.utils.extensions.launchWithDelay
import com.pokedex.utils.extensions.navigateSafelyWithDirections
import com.pokedex.view.adapters.PokeAdapter
import com.pokedex.viewmodel.PokeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PokeListFragment : BaseFragmentBinding<FragmentPokeListBinding>() {
    override fun bind() = FragmentPokeListBinding.inflate(layoutInflater)

    private val pokeViewModel: PokeViewModel by viewModel()

    override fun onViewCreated() {
        setObservers()
        updatePokeList()
    }

    private fun updatePokeList() {
        when {
            shouldShowRemotePokes -> {
                shouldShowRemotePokes = false
                pokeViewModel.getPokes()
            }
            shouldShowLocalPokes -> {
                shouldShowLocalPokes = false
                pokeViewModel.showLocalPokes()
                lifecycleScope.launchWithDelay(FIVE_SECONDS) {
                    pokeViewModel.rcvState = binding.rcvPokes.layoutManager?.onSaveInstanceState()
                    pokeViewModel.getPokes()
                }
            }
        }
    }

    private fun setObservers() {
        pokeViewModel.pokes.observe(viewLifecycleOwner, { pokes ->
            binding.rcvPokes.layoutManager = LinearLayoutManager(requireContext())
            binding.rcvPokes.adapter = PokeAdapter(pokes, ::onPokeClicked)
            pokeViewModel.rcvState?.let { binding.rcvPokes.layoutManager?.onRestoreInstanceState(it) }
        })
    }

    private fun onPokeClicked(poke: Pokemon) {
        val directions = PokeListFragmentDirections.actionPokeListFragmentToPokeFragment(poke)
        navigateSafelyWithDirections(directions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        pokeViewModel.rcvState = binding.rcvPokes.layoutManager?.onSaveInstanceState()
    }

    companion object {
        private const val FIVE_SECONDS = 5000L
    }
}