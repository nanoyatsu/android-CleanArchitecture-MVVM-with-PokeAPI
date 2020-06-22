package com.nanoyatsu.example.clean.poke.domain.poke

interface PokeRepository {
    fun get(id: Int): PokeDetail
    fun list(offset: Int, limit: Int): List<PokeNameImage>
}