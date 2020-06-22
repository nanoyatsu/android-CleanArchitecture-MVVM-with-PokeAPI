package com.nanoyatsu.example.clean.poke.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
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

    companion object {
        private var INSTANCE: PokeDataBase? = null

        /**
         * KoinにPokeDataBase_Implの生成を教えることができないのでcompanion objectで生成
         */
        @Synchronized
        fun getInstance(appContext: Context): PokeDataBase {
            if (INSTANCE is PokeDataBase) return INSTANCE!!
            INSTANCE = Room.databaseBuilder(
                appContext,
                PokeDataBase::class.java,
                "poke_database"
            ).build()
            return INSTANCE!!
        }
    }
}