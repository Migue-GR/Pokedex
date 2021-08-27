package com.pokedex.model.remote

import com.google.gson.annotations.SerializedName

data class AbilitiesResponse(
    @SerializedName("name")
    val pokeName: String?,
    val abilities: List<RemoteAbilityDetails?>?
)