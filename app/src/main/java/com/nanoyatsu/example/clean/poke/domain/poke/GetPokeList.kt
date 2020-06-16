package com.nanoyatsu.example.clean.poke.domain.poke

import com.nanoyatsu.example.clean.poke.core.base.UseCase
import com.nanoyatsu.example.clean.poke.core.dataclass.Pagination
import me.sargunvohra.lib.pokekotlin.model.NamedApiResourceList

class GetPokeList(private val repo: PokeRepositoryInterface) :
    UseCase<Pagination, NamedApiResourceList>() {
    override suspend fun run(params: Pagination): NamedApiResourceList {
        // todo add image data (use -> https://raw.githubusercontent.com/fanzeyi/pokemon.json/master/sprites/001MS.png )
        return repo.list(params.offset, params.limit)
    }
}