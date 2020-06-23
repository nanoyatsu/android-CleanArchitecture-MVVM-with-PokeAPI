package com.nanoyatsu.example.clean.poke.domain.poke

import com.nanoyatsu.example.clean.poke.core.dataclass.Listing

interface PokeRepository {
    fun get(id: Int): PokeDetail
    fun list(): Listing<PokeNameImage>
}