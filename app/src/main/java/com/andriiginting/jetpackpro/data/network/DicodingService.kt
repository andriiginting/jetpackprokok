package com.andriiginting.jetpackpro.data.network

import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.utils.Network.MOVIE_POPULAR
import com.andriiginting.jetpackpro.utils.Network.TV_POPULAR
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DicodingService {
    @GET(MOVIE_POPULAR)
    fun getPopularMovies(@Query("api_key") apiKey: String): Single<MovieResponse>

    @GET(TV_POPULAR)
    fun getPopularTvShow(@Query("api_key") apiKey: String): Single<MovieResponse>

}