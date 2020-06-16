package com.nanoyatsu.example.clean.poke.data.repository

import com.nanoyatsu.example.clean.poke.data.resource.PokeDataSource
import com.nanoyatsu.example.clean.poke.domain.poke.PokeRepositoryInterface
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList
import me.sargunvohra.lib.pokekotlin.model.Pokemon

class PokeRepository(private val dataSource: PokeDataSource) : PokeRepositoryInterface {
    // todo Room cache
    override fun get(id: Int): Pokemon {
        return dataSource.get(id)
    }

    override fun list(offset: Int, limit: Int): NamedApiResourceList {
        return dataSource.list(offset, limit)
    }
}