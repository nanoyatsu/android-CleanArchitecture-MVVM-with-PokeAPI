package com.nanoyatsu.example.clean.poke.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import me.sargunvohra.lib.pokekotlin.model.PokemonType

@Entity(tableName = "poke_type_cache", primaryKeys = ["poke_id", "slot"])
data class PokeTypeCache(
    @ColumnInfo(name = "poke_id") val pokeId: Int,
    val slot: Int,
    @ColumnInfo(name = "type_id") val typeId: Int
) {
    companion object {
        fun from(pokeId: Int, data: PokemonType): PokeTypeCache {
            return PokeTypeCache(pokeId, data.slot, data.type.id)
        }
    }
}