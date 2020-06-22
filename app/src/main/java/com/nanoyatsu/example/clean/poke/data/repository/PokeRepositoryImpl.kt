package com.nanoyatsu.example.clean.poke.data.repository

import com.nanoyatsu.example.clean.poke.core.extension.pipe
import com.nanoyatsu.example.clean.poke.data.database.PokeDataBase
import com.nanoyatsu.example.clean.poke.data.database.relation.PokeCacheWithTypeAndAbility
import com.nanoyatsu.example.clean.poke.data.resource.PokeDataSource
import com.nanoyatsu.example.clean.poke.domain.poke.PokeDetail
import com.nanoyatsu.example.clean.poke.domain.poke.PokeNameImage
import com.nanoyatsu.example.clean.poke.domain.poke.PokeRepository

class PokeRepositoryImpl(private val dataSource: PokeDataSource, private val db: PokeDataBase) :
    PokeRepository {
    // todo Room cache
    override fun get(id: Int): PokeDetail {
        return dataSource.get(id)
            .pipe(PokeCacheWithTypeAndAbility.Companion::from)
            .pipe(PokeDetail.Companion::from)
        // db.pokeDao().getPoke(id)
    }

    override fun list(offset: Int, limit: Int): List<PokeNameImage> {
        return dataSource.list(offset, limit).results.map { PokeNameImage.from(it) }
    }
}