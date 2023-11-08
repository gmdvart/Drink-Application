package com.example.punkapplication.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.punkapplication.data.local.converters.StringArrayConverter
import com.example.punkapplication.data.local.dao.DrinkDao
import com.example.punkapplication.data.local.dao.DrinkFavoriteDao
import com.example.punkapplication.data.local.dao.DrinkHistoryDao
import com.example.punkapplication.data.local.dao.RemoteKeysDao

@Database(
    entities = [DrinkEntity::class, RemoteKeys::class, DrinkFavoriteEntity::class, DrinkHistoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringArrayConverter::class)
abstract class DrinkDatabase : RoomDatabase() {
    abstract val drinkDao: DrinkDao
    abstract val remoteKeysDao: RemoteKeysDao
    abstract val drinkFavoriteDao: DrinkFavoriteDao
    abstract val drinkHistoryDao: DrinkHistoryDao

    companion object {
        private const val DATABASE_NAME = "punk_db"

        @Volatile
        private var INSTANCE: DrinkDatabase? = null

        fun getDatabase(context: Context): DrinkDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context): DrinkDatabase {
            return Room.databaseBuilder(
                name = DATABASE_NAME,
                context = context,
                klass = DrinkDatabase::class.java
            ).build()
        }
    }
}