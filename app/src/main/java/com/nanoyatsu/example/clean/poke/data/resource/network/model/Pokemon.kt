package com.nanoyatsu.example.clean.poke.data.resource.network.model

/***
 * 脱PokeKotlinのため、とりあえずビルドが通るようにする
 */
data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val stats: List<PokemonStat>,
    val sprites: PokemonSprites,
    val types: List<PokemonType>,
    val abilities: List<PokemonAbility>
)