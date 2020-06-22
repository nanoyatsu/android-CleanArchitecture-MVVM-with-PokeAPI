package com.nanoyatsu.example.clean.poke.data.repository

import com.nanoyatsu.example.clean.poke.data.resource.PokeDataSource
import com.nanoyatsu.example.clean.poke.domain.poke.PokeDetail
import com.nanoyatsu.example.clean.poke.domain.poke.PokeNameImage
import com.nanoyatsu.example.clean.poke.domain.poke.PokeRepository

class PokeRepositoryImpl(private val dataSource: PokeDataSource) : PokeRepository {
    // todo Room cache
    override fun get(id: Int): Pokemon {
        return dataSource.get(id)
    }

    override fun list(offset: Int, limit: Int): List<PokeNameImage> {
        return dataSource.list(offset, limit).results.map { PokeNameImage.from(it) }
    }
}