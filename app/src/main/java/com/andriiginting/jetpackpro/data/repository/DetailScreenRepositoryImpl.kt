package com.andriiginting.jetpackpro.data.repository

import com.andriiginting.jetpackpro.BuildConfig
import com.andriiginting.jetpackpro.data.database.TheaterDAO
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.data.model.MovieItem
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.data.network.DicodingService
import com.andriiginting.jetpackpro.utils.mapToTheater
import io.reactivex.Completable
import io.reactivex.Single

interface DetailScreenRepositoryContract {
    fun getSimilarMovie(id: String): Single<MovieResponse>
    fun getSimilarTvShow(id: String): Single<MovieResponse>
    fun saveToDatabase(data: MovieItem, type: String): Completable
    fun isFavoriteTheater(id: String): Single<TheaterFavorite>
    fun deleteTheaterData(movieId: String): Completable
}

class DetailScreenRepository(
    private val service: DicodingService,
    private val database: TheaterDAO
) : DetailScreenRepositoryContract {

    override fun saveToDatabase(data: MovieItem, type: String): Completable {
        val favoriteData = data.mapToTheater(type)
        return database.insertFavoriteTheater(favoriteData)
    }

    override fun getSimilarMovie(id: String) = service.getSimilarMovie(id, BuildConfig.API_KEY)

    override fun getSimilarTvShow(id: String) = service.getSimilarTvShow(id, BuildConfig.API_KEY)

    override fun isFavoriteTheater(id: String): Single<TheaterFavorite> = database.isFavorite(id.toInt())

    override fun deleteTheaterData(movieId: String): Completable = database.deleteTheater(movieId)
}