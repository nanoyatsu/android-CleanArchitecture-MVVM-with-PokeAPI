package com.nanoyatsu.example.clean.poke.domain.pokeDetail

interface PokeDetailRepository {
    suspend fun get(id: Int): PokeDetail
    suspend fun refresh(id: Int): PokeDetail
}