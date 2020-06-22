package com.nanoyatsu.example.clean.poke.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

@Entity(tableName = "type_index_cache")
data class TypeIndexCache(
    @PrimaryKey val id: Int,
    val name: String
) {
    companion object {
        fun from(data: NamedApiResource) {
            TypeIndexCache(data.id, data.name)
        }
    }
}