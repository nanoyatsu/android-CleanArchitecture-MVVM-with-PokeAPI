package com.nanoyatsu.example.clean.poke.domain.poke

import com.nanoyatsu.example.clean.poke.core.base.UseCase
import com.nanoyatsu.example.clean.poke.core.dataclass.Listing

class GetPokeList(private val repo: PokeRepository) :
    UseCase<Unit, Listing<PokeNameImage>>() {
    override suspend fun run(params: Unit): Listing<PokeNameImage> {
        return repo.list()
    }
}