package com.nanoyatsu.example.clean.poke.data.resource.network.model

/***
 * 脱PokeKotlinのため、とりあえずビルドが通るようにする
 */
data class NamedApiResource(
    val category: String,
    val id: Int,
    val name: String
) {
}