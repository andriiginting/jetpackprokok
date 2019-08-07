package com.andriiginting.jetpackpro.data.repository

import com.andriiginting.jetpackpro.BuildConfig
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.data.network.DicodingService
import io.reactivex.Single

interface DetailScreenRepositoryContract {
    fun getSimilarMovie(id: String): Single<MovieResponse>
    fun getSimilarTvShow(id: String): Single<MovieResponse>
}

class DetailScreenRepository(
    private val service: DicodingService
) : DetailScreenRepositoryContract{
    override fun getSimilarMovie(id: String) = service.getSimilarMovie(id, BuildConfig.API_KEY)

    override fun getSimilarTvShow(id: String) = service.getSimilarTvShow(id, BuildConfig.API_KEY)
}