package com.andriiginting.jetpackpro.data.repository

import com.andriiginting.jetpackpro.BuildConfig
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.data.model.TvResponse
import com.andriiginting.jetpackpro.data.network.DicodingService
import io.reactivex.Single

interface HomeRepositoryContract {
    fun getPopularMovies(): Single<MovieResponse>
    fun getTvShowMovie(): Single<TvResponse>
}

class HomeRepository(
    private val services: DicodingService
): HomeRepositoryContract {

    override fun getPopularMovies(): Single<MovieResponse> = services.getPopularMovies(BuildConfig.API_KEY)

    override fun getTvShowMovie(): Single<TvResponse> = services.getPopularTvShow(BuildConfig.API_KEY)
}