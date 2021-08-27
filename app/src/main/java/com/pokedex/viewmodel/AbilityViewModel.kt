package com.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import com.pokedex.repository.AbilityRepository
import com.pokedex.utils.extensions.launchUseCase

class AbilityViewModel(private val repository: AbilityRepository) : ViewModel() {
    val abilities = repository.abilities

    fun getAbilitiesByName(name: String) = launchUseCase {
        repository.getAbilitiesByName(name)
    }
}