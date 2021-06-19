package com.nanoyatsu.example.clean.poke.data.resource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nanoyatsu.example.clean.poke.data.resource.network.model.NamedApiResource

@Entity(tableName = "ability_index_cache")
data class AbilityIndexCache(
    @PrimaryKey val id: Int,
    val name: String
) {
    companion object {
        fun from(data: NamedApiResource): AbilityIndexCache {
            return AbilityIndexCache(data.id, data.name)
        }
    }
}