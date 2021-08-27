package com.pokedex.viewmodel

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import com.pokedex.repository.PokeRepository
import com.pokedex.utils.extensions.launchUseCase

class PokeViewModel(private val repository: PokeRepository) : ViewModel() {
    var rcvState: Parcelable? = null
    val pokes = repository.pokes
    val poke = repository.poke

    fun getPokes(limit: Int = 151) = launchUseCase {
        repository.getPokes(limit)
    }

    fun showLocalPokes() = launchUseCase {
        repository.showLocalPokes()
    }

    fun getPokeByName(name: String) = launchUseCase {
        repository.getPokeByName(name)
    }
}