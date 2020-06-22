package com.nanoyatsu.example.clean.poke.data.database.dao

import androidx.room.*
import com.nanoyatsu.example.clean.poke.data.database.relation.PokeCacheWithTypeAndAbility

@Dao
interface PokeDao {
    @Transaction
    @Query("Select * FROM poke_cache WHERE id == :id")
    fun getPoke(id: Int): List<PokeCacheWithTypeAndAbility>

    // fixme それぞれにinsert
//    @Transaction
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(row: PokeCacheWithTypeAndAbility)
}