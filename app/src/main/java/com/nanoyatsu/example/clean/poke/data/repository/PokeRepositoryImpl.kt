package com.nanoyatsu.example.clean.poke.data.repository

import com.nanoyatsu.example.clean.poke.data.database.PokeDataBase
import com.nanoyatsu.example.clean.poke.data.database.dao.PokeDao
import com.nanoyatsu.example.clean.poke.data.database.relation.PokeCacheWithTypeAndAbility
import com.nanoyatsu.example.clean.poke.data.resource.PokeDataSource
import com.nanoyatsu.example.clean.poke.domain.poke.PokeDetail
import com.nanoyatsu.example.clean.poke.domain.poke.PokeNameImage
import com.nanoyatsu.example.clean.poke.domain.poke.PokeRepository

class PokeRepositoryImpl(private val dataSource: PokeDataSource, db: PokeDataBase) :
    PokeRepository {
    private val detailDao = db.pokeDao()

    override fun get(id: Int): PokeDetail {
        val dbModel = detailDao.getPoke(id)
            ?: fromNetworkWithCaching(id, detailDao)
        return PokeDetail.from(dbModel)
    }

    private fun fromNetworkWithCaching(id: Int, dao: PokeDao): PokeCacheWithTypeAndAbility {
        val convertedApiResult = dataSource.get(id)
            .let(PokeCacheWithTypeAndAbility.Companion::from)
        insertPokeCacheWithTypeAndAbility(convertedApiResult, dao)
        return convertedApiResult
    }

    private fun insertPokeCacheWithTypeAndAbility(row: PokeCacheWithTypeAndAbility, dao: PokeDao) {
        dao.insertPoke(row.poke)
        row.types.map { it.type }.let(dao::insertAllTypeIndex)
        row.types.map { it.pokeType }.let(dao::insertAllPokeType)
        row.abilities.map { it.ability }.let(dao::insertAllAbilityIndex)
        row.abilities.map { it.pokeAbility }.let(dao::insertAllPokeAbility)
    }

    override fun list(offset: Int, limit: Int): List<PokeNameImage> {
        return dataSource.list(offset, limit).results.map { PokeNameImage.from(it) }
    }
}