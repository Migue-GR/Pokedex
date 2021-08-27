package com.pokedex.datasource.remote

import com.pokedex.model.remote.AbilitiesResponse
import com.pokedex.model.remote.EvolutionChainResponse
import com.pokedex.model.remote.PokeListResponse
import com.pokedex.model.remote.RemotePokemon
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface PokeService {
    @GET("pokemon")
    suspend fun getPokes(@Query("limit") limit: Int): PokeListResponse

    @GET("pokemon-species/{name}")
    suspend fun getPokeByName(@Path("name") name: String): RemotePokemon

    @GET("pokemon/{name}")
    suspend fun getAbilitiesByName(@Path("name") name: String): AbilitiesResponse

    @GET
    suspend fun getEvolutionChain(@Url url: String): EvolutionChainResponse
}