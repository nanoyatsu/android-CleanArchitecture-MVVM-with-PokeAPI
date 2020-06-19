package com.nanoyatsu.example.clean.poke.data.repository

import com.nanoyatsu.example.clean.poke.data.resource.PokeDataSource
import com.nanoyatsu.example.clean.poke.domain.poke.PokeRepository
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList
import me.sargunvohra.lib.pokekotlin.model.Pokemon

class PokeRepositoryImpl(private val dataSource: PokeDataSource) : PokeRepository {
    // todo Room cache
    override fun get(id: Int): Pokemon {
        return dataSource.get(id)
    }

    override fun list(offset: Int, limit: Int): NamedApiResourceList {
        return dataSource.list(offset, limit)
    }
}