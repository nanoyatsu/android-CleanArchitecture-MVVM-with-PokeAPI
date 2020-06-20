package com.nanoyatsu.example.clean.poke.domain.poke

import com.nanoyatsu.example.clean.poke.core.base.UseCase
import me.sargunvohra.lib.pokekotlin.model.Pokemon

class GetPoke(private val repo: PokeRepository) :
    UseCase<Int, Pokemon>() {
    override suspend fun run(params: Int): Pokemon {
        return repo.get(params)
    }
}