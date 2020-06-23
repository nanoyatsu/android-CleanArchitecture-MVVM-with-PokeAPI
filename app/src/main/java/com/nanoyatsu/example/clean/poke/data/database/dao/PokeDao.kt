package com.nanoyatsu.example.clean.poke.data.database.dao

import androidx.room.*
import com.nanoyatsu.example.clean.poke.data.database.entity.*
import com.nanoyatsu.example.clean.poke.data.database.relation.PokeCacheWithTypeAndAbility

@Dao
interface PokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPokeIndex(row: List<PokeIndexCache>)

    @Query("DELETE FROM poke_index_cache")
    fun deleteAllPokeIndex()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoke(row: PokeCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTypeIndex(rows: List<TypeIndexCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPokeType(rows: List<PokeTypeCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAbilityIndex(rows: List<AbilityIndexCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPokeAbility(rows: List<PokeAbilityCache>)

    @Transaction
    @Query("SELECT * FROM poke_cache WHERE id == :id")
    fun getPoke(id: Int): PokeCacheWithTypeAndAbility?

    @Query("DELETE FROM poke_cache WHERE id == :id")
    fun deletePoke(id: Int)
}