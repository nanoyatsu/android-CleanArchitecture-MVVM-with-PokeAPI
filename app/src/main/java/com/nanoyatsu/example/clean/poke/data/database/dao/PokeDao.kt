package com.nanoyatsu.example.clean.poke.data.database.dao

import androidx.paging.DataSource
import androidx.room.*
import com.nanoyatsu.example.clean.poke.data.database.entity.*
import com.nanoyatsu.example.clean.poke.data.database.relation.PokeCacheWithTypeAndAbility

/**
 * Index用とDetail用あたりでDAO定義分けたほうが誠実
 */
@Dao
interface PokeDao {
    // index

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokeIndex(row: List<PokeIndexCache>)

    @Query("DELETE FROM poke_index_cache")
    suspend fun deleteAllPokeIndex()

    @Query("SELECT * FROM poke_index_cache ORDER BY number")
    fun getPokeIndex(): DataSource.Factory<Int, PokeIndexCache>

    // detail

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