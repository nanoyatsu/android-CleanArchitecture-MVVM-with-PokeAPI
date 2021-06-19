package com.nanoyatsu.example.clean.poke.data.resource.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.nanoyatsu.example.clean.poke.data.resource.database.entity.*
import com.nanoyatsu.example.clean.poke.data.resource.network.model.Pokemon
import com.nanoyatsu.example.clean.poke.data.resource.network.model.PokemonAbility
import com.nanoyatsu.example.clean.poke.data.resource.network.model.PokemonType

data class PokeCacheWithTypeAndAbility(
    @Embedded val poke: PokeCache,
    @Relation(entity = PokeTypeCache::class, parentColumn = "id", entityColumn = "poke_id")
    val types: List<PokeCacheTypeWithName>,
    @Relation(entity = PokeAbilityCache::class, parentColumn = "id", entityColumn = "poke_id")
    val abilities: List<PokeCacheAbilityWithName>
) {
    companion object {
        fun from(data: Pokemon): PokeCacheWithTypeAndAbility {
            return PokeCacheWithTypeAndAbility(
                poke = PokeCache.from(data),
                types = data.types.map { PokeCacheTypeWithName.from(data.id, it) },
                abilities = data.abilities.map { PokeCacheAbilityWithName.from(data.id, it) }
            )
        }
    }
}


class PokeCacheTypeWithName(
    @Embedded val pokeType: PokeTypeCache,
    @Relation(parentColumn = "type_id", entityColumn = "id")
    val type: TypeIndexCache
) {
    companion object {
        fun from(pokeId: Int, data: PokemonType): PokeCacheTypeWithName {
            return PokeCacheTypeWithName(
                PokeTypeCache.from(pokeId, data),
                TypeIndexCache.from(data.type)
            )
        }
    }
}

class PokeCacheAbilityWithName(
    @Embedded val pokeAbility: PokeAbilityCache,
    @Relation(parentColumn = "ability_id", entityColumn = "id")
    val ability: AbilityIndexCache
) {
    companion object {
        fun from(pokeId: Int, data: PokemonAbility): PokeCacheAbilityWithName {
            return PokeCacheAbilityWithName(
                PokeAbilityCache.from(pokeId, data),
                AbilityIndexCache.from(data.ability)
            )
        }
    }
}