package com.nanoyatsu.example.clean.poke.domain.poke

import com.nanoyatsu.example.clean.poke.core.dataclass.Listing

/**
 * detailとlistあたりでRepositoryクラス分けたほうが誠実
 */
interface PokeRepository {
    suspend fun get(id: Int): PokeDetail
    suspend fun list(): Listing<PokeNameImage>
}