package com.nanoyatsu.example.clean.poke.data.resource.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.nanoyatsu.example.clean.poke.data.resource.network.pokeApi.query.FetchPokeDetailQuery
import com.nanoyatsu.example.clean.poke.data.resource.network.pokeApi.query.FetchPokedexQuery
import com.nanoyatsu.example.clean.poke.data.resource.network.pokeApi.type.Int_comparison_exp
import com.nanoyatsu.example.clean.poke.data.resource.network.pokeApi.type.Pokemon_v2_pokemon_bool_exp


class PokeNetworkResource(private val client: ApolloClient) {
    companion object {
        const val FETCH_POKEMONS_COUNT = 151
    }

    suspend fun get(id: Int): Response<FetchPokeDetailQuery.Data> {
        val where = Pokemon_v2_pokemon_bool_exp(
            id = Input.optional(Int_comparison_exp(_eq = Input.optional(id)))
        )
        val query = FetchPokeDetailQuery(where)
        return client.query(query).toDeferred().await()
    }

    suspend fun list(offset: Int, limit: Int): Response<FetchPokedexQuery.Data> {
        val query = FetchPokedexQuery(offset, limit)
        return client.query(query).toDeferred().await()
    }
}