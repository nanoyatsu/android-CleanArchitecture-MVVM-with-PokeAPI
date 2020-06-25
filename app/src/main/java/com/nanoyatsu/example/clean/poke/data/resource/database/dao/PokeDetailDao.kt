package com.nanoyatsu.example.clean.poke.data.resource.database.dao

import androidx.room.*
import com.nanoyatsu.example.clean.poke.data.resource.database.entity.*
import com.nanoyatsu.example.clean.poke.data.resource.database.relation.PokeCacheWithTypeAndAbility

@Dao
interface PokeDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoke(row: PokeCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTypeIndex(rows: List<TypeIndexCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokeType(rows: List<PokeTypeCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAbilityIndex(rows: List<AbilityIndexCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokeAbility(rows: List<PokeAbilityCache>)

    @Transaction
    @Query("SELECT * FROM poke_cache WHERE id == :id")
    suspend fun getPoke(id: Int): PokeCacheWithTypeAndAbility?

    @Query("DELETE FROM poke_cache WHERE id == :id")
    suspend fun deletePoke(id: Int)
}