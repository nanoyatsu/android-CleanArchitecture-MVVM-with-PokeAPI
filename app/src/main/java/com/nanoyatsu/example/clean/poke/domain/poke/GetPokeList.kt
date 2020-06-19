package com.nanoyatsu.example.clean.poke.domain.poke

import com.nanoyatsu.example.clean.poke.core.base.UseCase
import com.nanoyatsu.example.clean.poke.core.dataclass.Pagination

class GetPokeList(private val repo: PokeRepository) :
        UseCase<Pagination, List<PokeNameImage>>() {
    override suspend fun run(params: Pagination): List<PokeNameImage> {
        // NamedApiResourceList の next, previous とかのメタ情報たぶんそのうち使う気がする
        val list = repo.list(params.offset, params.limit)
        return list.results.map { PokeNameImage.from(it) }
    }
}