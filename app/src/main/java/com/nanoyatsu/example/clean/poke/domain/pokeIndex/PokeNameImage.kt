package com.nanoyatsu.example.clean.poke.domain.pokeIndex

import com.nanoyatsu.example.clean.poke.core.extension.proper
import com.nanoyatsu.example.clean.poke.data.database.entity.PokeIndexCache
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

data class PokeNameImage(
    val name: String,
    val number: Int
) {
    val imageUrl: String
        get() = generateUrl(number)

    /**
     * @return 画像リソース元 https://github.com/fanzeyi/pokemon.json
     */
    private fun generateUrl(id: Int): String {
        val adjusted = String.format("%03d", id)
        return "https://raw.githubusercontent.com/fanzeyi/pokemon.json/master/sprites/${adjusted}MS.png"
    }

    companion object {
        fun from(data: PokeIndexCache): PokeNameImage {
            return PokeNameImage(
                data.name,
                data.number
            )
        }

        fun from(resource: NamedApiResource): PokeNameImage {
            return PokeNameImage(
                resource.name.proper(),
                resource.id
            )
        }
    }
}