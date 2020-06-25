package com.nanoyatsu.example.clean.poke.domain.pokeIndex

import com.nanoyatsu.example.clean.poke.core.dataclass.Listing

interface PokeIndexRepository {
    suspend fun list(): Listing<PokeNameImage>
}