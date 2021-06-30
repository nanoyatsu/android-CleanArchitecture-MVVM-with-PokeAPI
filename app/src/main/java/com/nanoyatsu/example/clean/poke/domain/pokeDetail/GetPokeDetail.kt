package com.nanoyatsu.example.clean.poke.domain.pokeDetail

import com.nanoyatsu.example.clean.poke.core.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetPokeDetail(private val repo: PokeDetailRepository) :
    UseCase<Int, PokeDetail>() {
    override suspend fun run(params: Int): PokeDetail {
        return withContext(Dispatchers.IO) {
            repo.get(params)
        }
    }
}