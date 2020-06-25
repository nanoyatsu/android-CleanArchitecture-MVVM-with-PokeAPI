package com.nanoyatsu.example.clean.poke.data.repository

import com.nanoyatsu.example.clean.poke.data.database.dao.PokeDao
import com.nanoyatsu.example.clean.poke.data.database.relation.PokeCacheWithTypeAndAbility
import com.nanoyatsu.example.clean.poke.data.resource.PokeNetworkResource
import com.nanoyatsu.example.clean.poke.domain.pokeDetail.PokeDetail
import com.nanoyatsu.example.clean.poke.domain.pokeDetail.PokeDetailRepository

class PokeDetailRepositoryImpl(
    private val networkResource: PokeNetworkResource,
    private val dao: PokeDao
) : PokeDetailRepository {
    override suspend fun get(id: Int): PokeDetail {
        val dbModel = dao.getPoke(id)
            ?: fromNetworkWithCaching(id, networkResource, dao)
        return PokeDetail.from(dbModel)
    }

    private suspend fun fromNetworkWithCaching(
        id: Int, api: PokeNetworkResource, dao: PokeDao
    ): PokeCacheWithTypeAndAbility {
        val convertedApiResult = api.get(id)
            .let(PokeCacheWithTypeAndAbility.Companion::from)
        insertPokeCacheWithTypeAndAbility(convertedApiResult, dao)
        return convertedApiResult
    }

    private suspend fun insertPokeCacheWithTypeAndAbility(
        row: PokeCacheWithTypeAndAbility, dao: PokeDao
    ) {
        dao.insertPoke(row.poke)
        row.types.map { it.type }.let { dao.insertAllTypeIndex(it) }
        row.types.map { it.pokeType }.let { dao.insertAllPokeType(it) }
        row.abilities.map { it.ability }.let { dao.insertAllAbilityIndex(it) }
        row.abilities.map { it.pokeAbility }.let { dao.insertAllPokeAbility(it) }
    }

    override suspend fun refresh(id: Int): PokeDetail {
        TODO("Not yet implemented")
    }
}