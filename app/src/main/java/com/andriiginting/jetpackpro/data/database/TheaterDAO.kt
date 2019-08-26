package com.andriiginting.jetpackpro.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andriiginting.jetpackpro.utils.Room.DELETE_FAVORITE_THEATER_WITH_ID
import com.andriiginting.jetpackpro.utils.Room.FILTER_FAVORITE_THEATER_WITH_ID
import com.andriiginting.jetpackpro.utils.Room.GET_ALL_FAVORITE_THEATER
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TheaterDAO {
    @Query(GET_ALL_FAVORITE_THEATER)
    fun getAllFavoriteTheater(): DataSource.Factory<Int, TheaterFavorite>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteTheater(data: TheaterFavorite): Completable

    @Query(FILTER_FAVORITE_THEATER_WITH_ID)
    fun isFavorite(id: Int): Single<TheaterFavorite>

    @Query(DELETE_FAVORITE_THEATER_WITH_ID)
    fun deleteTheater(movieId: String): Completable
}