package com.nanoyatsu.example.clean.poke.data.resource.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nanoyatsu.example.clean.poke.data.resource.database.entity.PokeIndexCache

@Dao
interface PokeIndexDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokeIndex(row: List<PokeIndexCache>)

    @Query("DELETE FROM poke_index_cache")
    suspend fun deleteAllPokeIndex()

    @Query("SELECT * FROM poke_index_cache ORDER BY number")
    fun getPokeIndex(): DataSource.Factory<Int, PokeIndexCache>
}