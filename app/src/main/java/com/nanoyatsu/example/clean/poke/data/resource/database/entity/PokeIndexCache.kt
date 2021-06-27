package com.nanoyatsu.example.clean.poke.data.resource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nanoyatsu.example.clean.poke.data.resource.network.graphql.pokemon.query.FetchPokemonsQuery

@Entity(tableName = "poke_index_cache")
data class PokeIndexCache(
    @PrimaryKey val number: Int,
    val name: String
) {
    companion object {
        fun from(data: FetchPokemonsQuery.Pokemon): PokeIndexCache {
            val num = data.number?.toIntOrNull() ?: -1
            val name = data.name ?: "ERROR_NAME"
            return PokeIndexCache(num, name)
        }
    }
}