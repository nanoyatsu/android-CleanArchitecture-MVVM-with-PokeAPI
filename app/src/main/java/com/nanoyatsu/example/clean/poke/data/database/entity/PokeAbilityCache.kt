package com.nanoyatsu.example.clean.poke.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import me.sargunvohra.lib.pokekotlin.model.PokemonAbility

@Entity(tableName = "poke_ability_cache", primaryKeys = ["poke_id", "slot"])
data class PokeAbilityCache(
    @ColumnInfo(name = "poke_id") val pokeId: Int,
    val slot: Int,
    @ColumnInfo(name = "ability_id") val abilityId: Int,
    @ColumnInfo(name = "is_hidden") val isHidden: Boolean
) {
    companion object {
        fun from(pokeId: Int, data: PokemonAbility) {
            PokeAbilityCache(pokeId, data.slot, data.ability.id, data.isHidden)
        }
    }
}