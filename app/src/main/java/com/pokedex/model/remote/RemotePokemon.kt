package com.pokedex.model.remote

import com.google.gson.annotations.SerializedName

data class RemotePokemon(
    val name: String?,
    @SerializedName("base_happiness")
    val baseHappiness: Int?,
    @SerializedName("capture_rate")
    val captureRate: Int?,
    @SerializedName("egg_groups")
    val eggGroups: List<RemoteEggGroup?>?,
    @SerializedName("evolution_chain")
    val evolutionChainUrl: EvolutionChainUrl?
)