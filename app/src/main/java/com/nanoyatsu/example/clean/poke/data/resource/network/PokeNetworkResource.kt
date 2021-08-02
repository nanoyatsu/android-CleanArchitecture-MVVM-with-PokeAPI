package com.nanoyatsu.example.clean.poke.data.resource.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import com.nanoyatsu.example.clean.poke.data.resource.network.graphql.pokemon.query.FetchPokemonsQuery
import com.nanoyatsu.example.clean.poke.data.resource.network.graphql.pokemon.query.SearchPokemonQuery
import com.nanoyatsu.example.clean.poke.data.resource.network.pokeApi.query.FetchPokedexQuery


class PokeNetworkResource(private val client: ApolloClient) {
    companion object {
        const val FETCH_POKEMONS_COUNT = 151
    }

    suspend fun get(name: String): Response<SearchPokemonQuery.Data> {
        TODO("未定義のクエリ")
        val query = SearchPokemonQuery(name)
        return client.query(query).toDeferred().await()
    }
    // fun get(id: Int) = client.getPokemon(id)

    /**
     * `pokemons` queryが、 `first` として整数を１つだけ受け付ける。
     * 1だとNo.1のみ1件、151だとNo.1~151の151件返す。
     */
    suspend fun list(count: Int = FETCH_POKEMONS_COUNT): Response<FetchPokemonsQuery.Data> {
        val query = FetchPokemonsQuery(count)
        return client.query(query).toDeferred().await()
    }

    suspend fun list(offset: Int, limit: Int): Response<FetchPokedexQuery.Data> {
        val query = FetchPokedexQuery(offset, limit)
        return client.query(query).toDeferred().await()
    }
}