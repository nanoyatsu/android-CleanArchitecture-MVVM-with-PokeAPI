package com.nanoyatsu.example.clean.poke.data.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

@Entity(tableName = "ability_index_cache")
data class AbilityIndexCache(
    @PrimaryKey val id: Int,
    val name: String
) {
    companion object {
        fun from(data: NamedApiResource) {
            AbilityIndexCache(data.id, data.name)
        }
    }
}