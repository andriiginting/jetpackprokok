package com.andriiginting.jetpackpro.data.database

import android.app.Application
import androidx.room.Room
import com.andriiginting.jetpackpro.utils.Room.FAVORITE_DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TheaterDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): TheaterDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            TheaterDatabase::class.java,
            FAVORITE_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTheaterDAO(database: TheaterDatabase): TheaterDAO {
        return database.theaterDAO()
    }
}