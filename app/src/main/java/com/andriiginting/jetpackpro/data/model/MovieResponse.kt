package com.andriiginting.jetpackpro.data.model

import android.os.Parcelable
import com.andriiginting.jetpackpro.data.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse(
    @SerializedName("results") var resultsIntent: MutableList<MovieItem> = mutableListOf()
) : BaseResponse(), Parcelable

@Parcelize
data class MovieItem(
    @SerializedName("id") var id: String = "",
    @SerializedName("movie_id") var movieId: String = "",
    @SerializedName("original_title") var title: String = "",
    @SerializedName("poster_path") var posterPath: String = "",
    @SerializedName("overview") var overview: String = "",
    @SerializedName("backdrop_path") var backdropPath: String = "",
    @SerializedName("release_date") var releaseDate: String = ""
) : Parcelable