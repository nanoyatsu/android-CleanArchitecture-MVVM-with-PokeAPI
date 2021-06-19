package com.nanoyatsu.example.clean.poke.data.resource.network.model

/***
 * 脱PokeKotlinのため、とりあえずビルドが通るようにする
 */
data class PokemonSprites(
    val backDefault: String?,
    val backShiny: String?,
    val frontDefault: String?,
    val frontShiny: String?,
    val backFemale: String?,
    val backShinyFemale: String?,
    val frontFemale: String?,
    val frontShinyFemale: String?
)