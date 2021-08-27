package com.pokedex.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.withTransaction
import com.pokedex.datasource.local.AppDataBase
import com.pokedex.datasource.remote.PokeService
import com.pokedex.model.local.Ability
import com.pokedex.model.remote.AbilitiesResponse
import com.pokedex.utils.extensions.convertToLocalAbilities
import timber.log.Timber

class AbilityRepository(
    private val remoteDataSource: PokeService,
    private val localDataSource: AppDataBase
) {
    private val _abilities = MutableLiveData<List<Ability>>()
    val abilities: LiveData<List<Ability>> = _abilities

    suspend fun getAbilitiesByName(name: String) {
        try {
            val response = remoteDataSource.getAbilitiesByName(name)
            updateLocalAbilities(response)
            showLocalAbilities(name)
        } catch (e: Exception) {
            Timber.e(e, "Error getting the list of abilities")
            showLocalAbilities(name)
        }
    }

    private suspend fun updateLocalAbilities(
        response: AbilitiesResponse
    ) = localDataSource.withTransaction {
        localDataSource.abilityDao.clearTable()
        localDataSource.abilityDao.insertItems(response.convertToLocalAbilities())
    }

    private suspend fun showLocalAbilities(pokeName: String) = localDataSource.withTransaction {
        _abilities.postValue(localDataSource.abilityDao.getItems(pokeName))
    }
}