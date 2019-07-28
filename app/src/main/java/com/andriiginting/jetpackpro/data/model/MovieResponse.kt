package com.andriiginting.jetpackpro.data.model

import com.andriiginting.jetpackpro.data.BaseResponse
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("results") val resultsIntent: List<MovieItem>
): BaseResponse()

data class MovieItem(
    @SerializedName("id") val id: String,
    @SerializedName("movie_id") val movieId: String,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("release_date") val releaseDate: String
)