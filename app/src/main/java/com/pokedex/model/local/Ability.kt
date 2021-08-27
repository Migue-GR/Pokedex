package com.pokedex.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "abilities")
data class Ability(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo val pokeName: String,
    @ColumnInfo val name: String
)