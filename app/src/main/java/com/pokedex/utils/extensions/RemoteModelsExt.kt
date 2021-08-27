package com.pokedex.utils.extensions

import com.pokedex.model.local.Ability
import com.pokedex.model.local.Pokemon
import com.pokedex.model.remote.*

fun List<RemotePokemon>.convertToLocalPokes(): List<Pokemon> {
    val pokes = mutableListOf<Pokemon>()
    for (item in this) {
        pokes.add(Pokemon(name = item.name ?: "MissingNo"))
    }
    return pokes
}

fun RemotePokemon.convertToLocalPoke(): Pokemon {
    var eggGroupsStr = ""
    eggGroups?.forEachIndexed { index, egg ->
        eggGroupsStr += if (index == 0) {
            egg?.name
        } else {
            ", ${egg?.name}"
        }
    }
    return Pokemon(
        name = name ?: "MissingNo",
        baseHappiness = baseHappiness ?: 0,
        captureRate = captureRate ?: 0,
        eggGroups = eggGroupsStr,
        evolutionChainUrl = evolutionChainUrl?.url ?: ""
    )
}

fun AbilitiesResponse.convertToLocalAbilities(): List<Ability> {
    val remoteAbilities = abilities?.filterNotNull() ?: listOf()
    val abilities = mutableListOf<Ability>()

    for (item in remoteAbilities) {
        abilities.add(
            Ability(
                pokeName = pokeName ?: "MissingNo",
                name = item.ability?.name ?: ""
            )
        )
    }
    return abilities
}

fun EvolutionChainResponse.convertToLocalEvolutionChain(): List<String> {
    val evolutionsToReturn = mutableListOf<String>()
    evolutionsToReturn.add(chain.species?.name ?: "")

    fun addEvolutionToTheList(evolutions: List<RemoteEvolution>) {
        for (evolution in evolutions) {
            evolutionsToReturn.add(evolution.species?.name ?: "MissingNo")
            if (!evolution.evolutions.isNullOrEmpty()) {
                addEvolutionToTheList(evolution.evolutions.filterNotNull())
            }
        }
    }

    addEvolutionToTheList(chain.evolutions?.filterNotNull() ?: listOf())
    return evolutionsToReturn
}