package com.nanoyatsu.example.clean.poke.domain.poke

import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

data class PokeNameImage(
    val name: String,
    val imageUrl: String?
) {
    companion object {
        fun create(resource: NamedApiResource): PokeNameImage {
//            val image = pickupId(resource) // fixme resource.url が無い・・・？
            return PokeNameImage(resource.name, null)
        }

        private fun pickupId(apiUrl: String): Int? {
            return apiUrl.replace(""".*/([0-9])+/\z""".toRegex(), "$1").toIntOrNull()
        }

        private fun generateUrl(id: Int): String? {
            val adjusted = String.format("%03d", id)
            return "https://raw.githubusercontent.com/fanzeyi/pokemon.json/master/sprites/${adjusted}MS.png"
        }
    }

}