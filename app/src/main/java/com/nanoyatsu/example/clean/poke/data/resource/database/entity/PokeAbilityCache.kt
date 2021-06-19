package com.nanoyatsu.example.clean.poke.data.resource.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.nanoyatsu.example.clean.poke.data.resource.network.model.PokemonAbility

@Entity(
    tableName = "poke_ability_cache",
    primaryKeys = ["poke_id", "ability_id"],
    indices = [Index(value = ["poke_id", "slot"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = PokeCache::class,
            parentColumns = ["id"],
            childColumns = ["poke_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AbilityIndexCache::class,
            parentColumns = ["id"],
            childColumns = ["ability_id"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class PokeAbilityCache(
    @ColumnInfo(name = "poke_id") val pokeId: Int,
    val slot: Int,
    @ColumnInfo(name = "ability_id") val abilityId: Int,
    @ColumnInfo(name = "is_hidden") val isHidden: Boolean
) {
    companion object {
        fun from(pokeId: Int, data: PokemonAbility): PokeAbilityCache {
            return PokeAbilityCache(pokeId, data.slot, data.ability.id, data.isHidden)
        }
    }
}