package com.nanoyatsu.example.clean.poke.data.resource.network.client

import com.nanoyatsu.example.clean.poke.data.resource.network.model.*

class DummyPokeApi {
    fun getPokemon(id: Int): Pokemon {
        if (id != 1) throw IllegalArgumentException("Now, api is dummy. Enabled id is 1 only.")
        return Pokemon(
            1, "bulbasaur", 7, 69,
            listOf(
                PokemonStat(1, 45), PokemonStat(2, 49),
                PokemonStat(3, 49), PokemonStat(4, 65),
                PokemonStat(5, 65), PokemonStat(6, 45)
            ),
            PokemonSprites(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/1.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/1.png",
                null, null, null, null
            ),
            listOf(
                PokemonType(NamedApiResource("type", 12, "grass"), 1),
                PokemonType(NamedApiResource("type", 4, "poison"), 2)
            ),
            listOf(
                PokemonAbility(NamedApiResource("ability", 65, "overgrow"), 1, false),
                PokemonAbility(NamedApiResource("ability", 34, "chlorophyll"), 1, true)
            )
        )
    }

    fun getPokemonList(offset: Int, limit: Int): NamedApiResourceList {
        if (offset != 0) return NamedApiResourceList(emptyList())
        return NamedApiResourceList(listOf(NamedApiResource("pokemon", 1, "bulbasaur")))
    }
}