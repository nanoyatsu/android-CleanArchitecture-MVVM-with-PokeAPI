package com.nanoyatsu.example.clean.poke.data.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.nanoyatsu.example.clean.poke.data.database.entity.*

data class PokeWithTypeAndAbility(
    @Embedded val poke: PokeCache,
    @Relation(entity = PokeTypeCache::class, parentColumn = "id", entityColumn = "poke_id")
    val types: List<PokeTypeWithName>,
    @Relation(entity = PokeAbilityCache::class, parentColumn = "id", entityColumn = "poke_id")
    val abilities: List<PokeAbilityWithName>
) {

}


class PokeTypeWithName(
    @Embedded val pokeType: PokeTypeCache,
    @Relation(parentColumn = "type_id", entityColumn = "id")
    val type: TypeIndexCache
)

class PokeAbilityWithName(
    @Embedded val pokeAbility: PokeAbilityCache,
    @Relation(parentColumn = "ability_id", entityColumn = "id")
    val ability: AbilityIndexCache
)