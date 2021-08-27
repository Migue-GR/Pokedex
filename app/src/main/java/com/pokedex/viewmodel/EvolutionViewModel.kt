package com.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import com.pokedex.repository.EvolutionRepository
import com.pokedex.utils.extensions.launchUseCase

class EvolutionViewModel(private val repository: EvolutionRepository) : ViewModel() {
    val evolutionChain = repository.evolutionChain

    fun getEvolutionChain(url: String) = launchUseCase {
        repository.getEvolutionChain(url)
    }

    fun markAsFavorite(name: String) = launchUseCase {
        repository.markAsFavorite(name)
    }
}