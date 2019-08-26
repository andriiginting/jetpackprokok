package com.andriiginting.jetpackpro.utils

object Network {
    const val MOVIE_POPULAR = "movie/popular"
    const val SIMILAR_MOVIE_POPULAR = "movie/{movie_id}/similar"
    const val TV_POPULAR = "tv/popular"
    const val SIMILAR_TV_POPULAR = "tv/{tv_id}/similar"
}

object IdleResources {
    const val DEFAULT_IDLE = 1
    const val DECREMENT_IDLE_RESOURCES = 0

    var idleResources = DEFAULT_IDLE
}

object Room {
    const val THEATER_TABLE_NAME = "favorite_theater"
    const val GET_ALL_FAVORITE_THEATER = "SELECT * FROM favorite_theater"
    const val FILTER_FAVORITE_THEATER_WITH_ID = "SELECT * FROM favorite_theater WHERE movie_id =:id LIMIT 1"
    const val DELETE_FAVORITE_THEATER_WITH_ID = "DELETE FROM favorite_theater WHERE movie_id = :movieId "
    const val FAVORITE_DATABASE_NAME = "favorite.db"
}