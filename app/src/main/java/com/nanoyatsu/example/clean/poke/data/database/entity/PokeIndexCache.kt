package com.nanoyatsu.example.clean.poke.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource
import java.util.*

@Entity(tableName = "poke_index_cache")
data class PokeIndexCache(
    @PrimaryKey val number: Int,
    val name: String
) {
    companion object {
        fun from(data: NamedApiResource): PokeIndexCache {
            if (data.category != "pokemon")
                throw InvalidPropertiesFormatException("required data.category == pokemon")
            return PokeIndexCache(data.id, data.name)
        }
    }
}