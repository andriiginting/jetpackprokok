package com.andriiginting.jetpackpro.domain

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.data.repository.FavoriteRepository
import com.andriiginting.jetpackpro.presentation.favorite.viewmodel.PAGE_SIZE
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

interface TheaterUseCaseMapper {
    fun getAllTheaterFavorite(): Flowable<PagedList<TheaterFavorite>>
}

class TheaterUseCase(
    private val repository: FavoriteRepository
) : TheaterUseCaseMapper {

    override fun getAllTheaterFavorite(): Flowable<PagedList<TheaterFavorite>> {
        return RxPagedListBuilder(repository.getFavoriteTheater(), PAGE_SIZE)
            .buildFlowable(BackpressureStrategy.BUFFER)
    }
}