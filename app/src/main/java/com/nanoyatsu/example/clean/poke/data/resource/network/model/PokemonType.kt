package com.nanoyatsu.example.clean.poke.data.resource.network.model

/***
 * 脱PokeKotlinのため、とりあえずビルドが通るようにする
 */
data class PokemonType(
    val type: NamedApiResource,
    val slot: Int
)