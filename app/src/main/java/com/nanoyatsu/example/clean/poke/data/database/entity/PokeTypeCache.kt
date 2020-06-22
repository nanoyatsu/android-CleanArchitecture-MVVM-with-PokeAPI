package com.nanoyatsu.example.clean.poke.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import me.sargunvohra.lib.pokekotlin.model.PokemonType

@Entity(
    tableName = "poke_type_cache",
    primaryKeys = ["poke_id", "type_id"],
    indices = [Index(value = ["poke_id", "slot"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = PokeCache::class,
            parentColumns = ["id"],
            childColumns = ["poke_id"],
            onDelete = ForeignKey.CASCADE
        )
        ,
        ForeignKey(
            entity = TypeIndexCache::class,
            parentColumns = ["id"],
            childColumns = ["type_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
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