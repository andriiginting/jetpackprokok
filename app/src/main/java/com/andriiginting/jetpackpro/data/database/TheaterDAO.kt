package com.andriiginting.jetpackpro.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andriiginting.jetpackpro.data.model.MovieItem
import com.andriiginting.jetpackpro.utils.Room.GET_FAVORITE_THEATER

@Dao
interface TheaterDAO {
    @Query(GET_FAVORITE_THEATER)
    fun getAllFavoriteTheater(): List<MovieItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFavoriteTheater(data: MovieItem)
}