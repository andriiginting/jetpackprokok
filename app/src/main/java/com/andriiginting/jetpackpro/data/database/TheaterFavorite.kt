package com.andriiginting.jetpackpro.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andriiginting.jetpackpro.utils.Room.THEATER_TABLE_NAME


@Entity(tableName = THEATER_TABLE_NAME)
data class TheaterFavorite(
    @ColumnInfo(name = "movie_id") var theaterFavoriteId: String? = "",
    @ColumnInfo(name = "original_title") var theaterTitle: String? = "",
    @ColumnInfo(name = "poster_path") var posterPath: String? = "",
    @ColumnInfo(name = "overview") var overview: String? = "",
    @ColumnInfo(name = "backdrop_path") var backdropPath: String? = "",
    @ColumnInfo(name = "release_data") var releaseDate: String? = "",
    @ColumnInfo(name = "screen_type") var type: String? = "",
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0
)