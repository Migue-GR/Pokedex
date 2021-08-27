package com.pokedex.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pokedex.model.local.Ability

@Dao
interface AbilityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<Ability>)

    @Query("SELECT * FROM abilities WHERE pokeName = (:pokeName)")
    suspend fun getItems(pokeName: String): List<Ability>

    @Query("DELETE FROM abilities")
    fun clearTable()
}