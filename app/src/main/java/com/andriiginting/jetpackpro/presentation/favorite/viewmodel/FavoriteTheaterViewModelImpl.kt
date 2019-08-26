package com.andriiginting.jetpackpro.presentation.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.andriiginting.jetpackpro.base.BaseViewModel
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.data.repository.FavoriteRepository
import com.andriiginting.jetpackpro.utils.plus
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable

const val PAGE_SIZE = 10

interface FavoriteTheaterViewModel {
    fun getFavoriteTheater()
}

class FavoriteTheaterViewModelImpl(
    repository: FavoriteRepository
) : FavoriteTheaterViewModel, BaseViewModel() {

    private val _state = MutableLiveData<FavoriteTheaterState>()
    val state: LiveData<FavoriteTheaterState>
        get() = _state

    private val favoriteList: Flowable<PagedList<TheaterFavorite>> =
        RxPagedListBuilder(repository.getFavoriteTheater(), PAGE_SIZE)
            .buildFlowable(BackpressureStrategy.BUFFER)

    override fun getFavoriteTheater() {
        addDisposable plus favoriteList
            .doOnSubscribe { _state.value = FavoriteTheaterState.ShowLoading }
            .doAfterTerminate { _state.value = FavoriteTheaterState.HideLoading }
            .subscribe({
                _state.value = FavoriteTheaterState.LoadFavoriteTheater(it)
            },{
              _state.value = FavoriteTheaterState.LoadScreenError
            })
    }
}

sealed class FavoriteTheaterState {
    object ShowLoading : FavoriteTheaterState()
    object HideLoading : FavoriteTheaterState()
    object LoadScreenError : FavoriteTheaterState()
    object LoadEmptyScreen : FavoriteTheaterState()

    data class LoadFavoriteTheater(val data: PagedList<TheaterFavorite>) : FavoriteTheaterState()
}