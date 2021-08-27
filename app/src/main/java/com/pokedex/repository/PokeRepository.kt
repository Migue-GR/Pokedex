package com.pokedex.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import com.pokedex.datasource.local.AppDataBase
import com.pokedex.datasource.remote.PokeService
import com.pokedex.model.local.Pokemon
import com.pokedex.model.remote.PokeListResponse
import com.pokedex.model.remote.RemotePokemon
import com.pokedex.utils.extensions.convertToLocalPoke
import com.pokedex.utils.extensions.convertToLocalPokes
import timber.log.Timber

class PokeRepository(
    private val remoteDataSource: PokeService,
    private val localDataSource: AppDataBase
) {
    private val _pokes = MutableLiveData<List<Pokemon>>()
    val pokes: LiveData<List<Pokemon>> = _pokes

    private val _poke = MutableLiveData<Pokemon>()
    val poke: LiveData<Pokemon> = _poke

    suspend fun getPokes(limit: Int) {
        try {
            val response = remoteDataSource.getPokes(limit)
            updateLocalPokes(response)
            showLocalPokes()
        } catch (e: Exception) {
            Timber.e(e, "Error getting the list of pokemon")
            showLocalPokes()
        }
    }

    suspend fun getPokeByName(name: String) {
        try {
            val response = remoteDataSource.getPokeByName(name)
            updateLocalPoke(response)
            showLocalPoke(name)
        } catch (e: Exception) {
            Timber.e(e, "Error getting the pokemon by its name")
            showLocalPoke(name)
        }
    }

    private suspend fun updateLocalPokes(listResponse: PokeListResponse) {
        val remotePokes = listResponse.results?.filterNotNull() ?: listOf()
        val localPokes = remotePokes.convertToLocalPokes()
        localDataSource.withTransaction {
            localDataSource.pokeDao.clearTable()
            localDataSource.pokeDao.insertItems(localPokes)
        }
    }

    suspend fun showLocalPokes() = localDataSource.withTransaction {
        _pokes.postValue(localDataSource.pokeDao.getItems())
    }

    private suspend fun updateLocalPoke(poke: RemotePokemon) = localDataSource.withTransaction {
        localDataSource.pokeDao.updateItem(poke.convertToLocalPoke())
    }

    private suspend fun showLocalPoke(name: String) = localDataSource.withTransaction {
        _poke.postValue(localDataSource.pokeDao.getItem(name))
    }
}