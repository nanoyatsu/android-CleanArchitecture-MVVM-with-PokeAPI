package com.nanoyatsu.example.clean.poke.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nanoyatsu.example.clean.poke.data.database.dao.IndexCacheDao
import com.nanoyatsu.example.clean.poke.data.database.dao.PokeDao
import com.nanoyatsu.example.clean.poke.data.database.entity.*

@Database(
    entities = [
        PokeIndexCache::class,
        TypeIndexCache::class,
        PokeTypeCache::class,
        AbilityIndexCache::class,
        PokeAbilityCache::class,
        PokeCache::class
    ],
    version = 1,
    exportSchema = true
)
abstract class PokeDataBase : RoomDatabase() {
    abstract fun indexCacheDao(): IndexCacheDao
    abstract fun pokeDao(): PokeDao
}