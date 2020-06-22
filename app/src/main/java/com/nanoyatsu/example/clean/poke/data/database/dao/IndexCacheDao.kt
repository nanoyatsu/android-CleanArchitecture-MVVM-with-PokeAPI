package com.nanoyatsu.example.clean.poke.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nanoyatsu.example.clean.poke.data.database.entity.PokeIndexCache

@Dao
interface IndexCacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(row: PokeIndexCache)

    @Query("DELETE FROM poke_index_cache")
    fun deleteAll()
}