package com.andriiginting.jetpackpro.data.model

import android.os.Parcelable
import com.andriiginting.jetpackpro.data.BaseResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvResponse(
    @SerializedName("results") val resultsIntent: List<TvItem>
) : BaseResponse(), Parcelable

@Parcelize
data class TvItem(
    @SerializedName("id") val id: String,
    @SerializedName("movie_id") val movieId: String,
    @SerializedName("original_name") val title: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("release_date") val releaseDate: String
) : Parcelable