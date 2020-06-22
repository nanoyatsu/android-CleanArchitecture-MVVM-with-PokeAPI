package com.nanoyatsu.example.clean.poke.domain.poke

import me.sargunvohra.lib.pokekotlin.model.Pokemon

interface PokeRepository {
    fun get(id: Int): Pokemon
    fun list(offset: Int, limit: Int): List<PokeNameImage>
}