package com.andriiginting.jetpackpro.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andriiginting.jetpackpro.utils.Room.FAVORITE_DATABASE_NAME

@Database(entities = [TheaterDatabase::class], version = 1)
abstract class TheaterDatabase : RoomDatabase() {
    abstract fun theaterDAO(): TheaterDAO

    companion object {
        private var INSTANCE: TheaterDatabase? = null

        fun getInstance(context: Context): TheaterDatabase? {
            if (INSTANCE == null) {
                synchronized(TheaterDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TheaterDatabase::class.java, FAVORITE_DATABASE_NAME
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun onDestroy() {
            INSTANCE = null
        }
    }
}