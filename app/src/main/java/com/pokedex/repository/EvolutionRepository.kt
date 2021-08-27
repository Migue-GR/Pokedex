package com.pokedex.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import com.pokedex.datasource.local.AppDataBase
import com.pokedex.datasource.remote.PokeService
import com.pokedex.model.enums.PokeError.ERROR_POKEMON_NOT_FOUND
import com.pokedex.model.enums.PokeError.FAIL_POKEMON_FAVORITE
import com.pokedex.model.enums.Success.SUCCESS_POKEMON_FAVORITE
import com.pokedex.model.remote.EvolutionChainResponse
import com.pokedex.utils.UiEventsManager.showError
import com.pokedex.utils.UiEventsManager.showSuccess
import com.pokedex.utils.extensions.convertToLocalEvolutionChain
import timber.log.Timber

class EvolutionRepository(
    private val remoteDataSource: PokeService,
    private val localDataSource: AppDataBase
) {
    private val _evolutionChain = MutableLiveData<List<String>>()
    val evolutionChain: LiveData<List<String>> = _evolutionChain

    suspend fun getEvolutionChain(url: String) {
        try {
            val response = remoteDataSource.getEvolutionChain(url)
            updateEvolutionChain(response)
        } catch (e: Exception) {
            Timber.e(e, "Error getting the evolution chain")
        }
    }

    suspend fun markAsFavorite(name: String) {
        localDataSource.withTransaction {
            val poke = localDataSource.pokeDao.getItem(name)
            if (poke == null) {
                showError(ERROR_POKEMON_NOT_FOUND)
            } else {
                val possibleResponses = listOf(FAIL_POKEMON_FAVORITE, SUCCESS_POKEMON_FAVORITE)
                if (possibleResponses.random() == SUCCESS_POKEMON_FAVORITE) {
                    localDataSource.pokeDao.updateItem(poke.copy(prefix = "Favorito - "))
                    showSuccess(SUCCESS_POKEMON_FAVORITE)
                } else {
                    localDataSource.pokeDao.updateItem(poke.copy(prefix = "Error - "))
                    showError(FAIL_POKEMON_FAVORITE)
                }
            }
        }
    }

    private fun updateEvolutionChain(response: EvolutionChainResponse) {
        _evolutionChain.postValue(response.convertToLocalEvolutionChain())
    }
}