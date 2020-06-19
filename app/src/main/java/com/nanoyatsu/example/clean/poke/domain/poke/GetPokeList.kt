package com.nanoyatsu.example.clean.poke.domain.poke

import com.nanoyatsu.example.clean.poke.core.base.UseCase
import com.nanoyatsu.example.clean.poke.core.dataclass.Pagination
import me.sargunvohra.lib.pokekotlin.model.NamedApiResource

class GetPokeList(private val repo: PokeRepositoryInterface) :
    UseCase<Pagination, List<NamedApiResource>>() {
    //    UseCase<Pagination, List<PokeNameImage>>() {
    override suspend fun run(params: Pagination): List<NamedApiResource> {
//    override suspend fun run(params: Pagination): List<PokeNameImage> {
        // NamedApiResourceList の next, previous とかのメタ情報たぶんそのうち使う気がする
        val list = repo.list(params.offset, params.limit)
        return list.results
//        return list.results.map { PokeNameImage.create(it) }
    }
}