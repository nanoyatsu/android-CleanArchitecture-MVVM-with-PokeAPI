package com.nanoyatsu.example.clean.poke.data.resource.network

import com.nanoyatsu.example.clean.poke.data.resource.network.client.DummyPokeApi


class PokeNetworkResource(private val client: DummyPokeApi) {
    fun get(id: Int) = client.getPokemon(id)
    fun list(offset: Int, limit: Int) = client.getPokemonList(offset, limit)
}