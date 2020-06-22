package com.nanoyatsu.example.clean.poke.domain.poke

import com.nanoyatsu.example.clean.poke.core.base.UseCase
import com.nanoyatsu.example.clean.poke.core.dataclass.Pagination

class GetPokeList(private val repo: PokeRepository) :
    UseCase<Pagination, List<PokeNameImage>>() {
    override suspend fun run(params: Pagination): List<PokeNameImage> {
        return repo.list(params.offset, params.limit)
    }
}