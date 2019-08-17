package com.andriiginting.jetpackpro.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andriiginting.jetpackpro.utils.Room.THEATER_TABLE_NAME

@Entity(tableName = THEATER_TABLE_NAME)
data class TheaterFavorite(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "favorite_id") var theaterFavoriteId: Long?,
    @ColumnInfo(name = "original_title") var theaterTitle: String?,
    @ColumnInfo(name = "poster_path") var posterPath: String?,
    @ColumnInfo(name = "backdrop_path") var backdropPath: String?,
    @ColumnInfo(name = "overview") var overview: String?,
    @ColumnInfo(name = "release_data") var releaseDate: String?
) {
    constructor() : this(null, "", "", "", "", "")
}