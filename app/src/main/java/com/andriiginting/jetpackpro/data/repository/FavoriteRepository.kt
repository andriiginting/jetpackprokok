package com.andriiginting.jetpackpro.data.repository

import androidx.paging.DataSource
import com.andriiginting.jetpackpro.data.database.TheaterDAO
import com.andriiginting.jetpackpro.data.database.TheaterDatabase
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import io.reactivex.Flowable
import io.reactivex.Single

interface FavoriteRepository {
    fun getFavoriteTheater(): DataSource.Factory<Int, TheaterFavorite>
}

class FavoriteRepositoryImpl(
    private val database: TheaterDAO
) : FavoriteRepository {

    override fun getFavoriteTheater(): DataSource.Factory<Int, TheaterFavorite> = database.getAllFavoriteTheater()
}