package com.nanoyatsu.example.clean.poke.data.resource

import me.sargunvohra.lib.pokekotlin.client.PokeApi

class PokeDataSource(private val client: PokeApi) {
    fun get(id: Int) = client.getPokemon(id)
    fun list(offset: Int, limit: Int) = client.getPokemonList(offset, limit)
}