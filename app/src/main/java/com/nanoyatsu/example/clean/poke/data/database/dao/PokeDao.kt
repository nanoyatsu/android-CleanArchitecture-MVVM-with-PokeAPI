package com.nanoyatsu.example.clean.poke.data.database.dao

import androidx.room.*
import com.nanoyatsu.example.clean.poke.data.database.relation.PokeCacheWithTypeAndAbility

@Dao
interface PokeDao {
    @Transaction
    @Query("Select * FROM poke_cache WHERE id == :id")
    suspend fun getPoke(id: Int): PokeCacheWithTypeAndAbility?

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(row: PokeCacheWithTypeAndAbility)
}