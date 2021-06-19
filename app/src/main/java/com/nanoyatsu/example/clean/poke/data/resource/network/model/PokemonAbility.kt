package com.nanoyatsu.example.clean.poke.data.resource.network.model

/***
 * 脱PokeKotlinのため、とりあえずビルドが通るようにする
 */
data class PokemonAbility(
    val ability: NamedApiResource,
    val slot: Int,
    val isHidden: Boolean
)