package com.pokedex.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pokedex.BuildConfig
import com.pokedex.model.local.Ability
import com.pokedex.model.local.Pokemon

@Database(
    version = BuildConfig.DB_VERSION,
    exportSchema = false,
    entities = [Pokemon::class, Ability::class]
)
abstract class AppDataBase : RoomDatabase() {
    abstract val pokeDao: PokeDao
    abstract val abilityDao: AbilityDao

    companion object {
        private const val DATABASE_NAME = "${BuildConfig.APPLICATION_ID}.AppDataBase"

        private fun createInstance(ctx: Context): AppDataBase =
            Room.databaseBuilder(ctx, AppDataBase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()

        fun getInstance(ctx: Context) = createInstance(ctx)
    }
}