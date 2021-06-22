package com.nanoyatsu.example.clean.poke.data.resource.network.client

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

const val GRAPHQL_POKEMON_ENDPOINT = "https://graphql-pokemon2.vercel.app/"

// 保持クラスはKoinのQualifierとして利用。（微妙な構成かも）
object GraphQlPokemonClient {
    private var instance: ApolloClient? = null
    fun get(): ApolloClient {
        return if (instance == null) generate(GRAPHQL_POKEMON_ENDPOINT)
        else instance!!
    }
}

// 別エンドポイントも使いたくなったらobject増やす

// [ApolloClient]生成関数
private fun generate(endpoint: String): ApolloClient {
    val okHttpClient = OkHttpClient.Builder()
        .build()

    return ApolloClient.builder()
        .serverUrl(endpoint)
        .okHttpClient(okHttpClient)
        .build()
}