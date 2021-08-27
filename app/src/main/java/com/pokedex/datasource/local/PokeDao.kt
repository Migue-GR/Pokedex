package com.pokedex.datasource.local

import androidx.room.*
import com.pokedex.model.local.Pokemon

@Dao
interface PokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<Pokemon>)

    @Update
    suspend fun updateItem(item: Pokemon)

    @Query("SELECT * FROM pokes")
    suspend fun getItems(): List<Pokemon>

    @Query("UPDATE pokes SET prefix = (:prefix) WHERE name = (:name)")
    suspend fun addPrefixToItem(prefix: String, name: String)

    @Query("SELECT * FROM pokes WHERE name = (:name)")
    suspend fun getItem(name: String): Pokemon?

    @Query("DELETE FROM pokes")
    fun clearTable()
}