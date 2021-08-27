package com.pokedex.model.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "pokes")
@Parcelize
data class Pokemon(
    @PrimaryKey val name: String,
    @ColumnInfo val baseHappiness: Int = 0,
    @ColumnInfo val captureRate: Int = 0,
    @ColumnInfo val eggGroups: String = "",
    @ColumnInfo val evolutionChainUrl: String = "",
    @ColumnInfo val prefix: String = ""
) : Parcelable