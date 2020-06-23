package com.nanoyatsu.example.clean.poke.data.database.dao

import androidx.room.*
import com.nanoyatsu.example.clean.poke.data.database.entity.*
import com.nanoyatsu.example.clean.poke.data.database.relation.PokeCacheWithTypeAndAbility

@Dao
interface PokeDao {
    @Transaction
    @Query("Select * FROM poke_cache WHERE id == :id")
    fun getPoke(id: Int): PokeCacheWithTypeAndAbility?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPoke(row: PokeCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTypeIndex(row: TypeIndexCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokeType(row: PokeTypeCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAbilityIndex(row: AbilityIndexCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokeAbility(row: PokeAbilityCache)

    // fixme それぞれにinsert
//    @Transaction
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(row: PokeCacheWithTypeAndAbility)
}