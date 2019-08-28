package com.andriiginting.jetpackpro.presentation.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.andriiginting.jetpackpro.TheaterBaseViewModel
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.domain.TheaterUseCaseMapper
import com.andriiginting.jetpackpro.utils.IdleResources
import com.andriiginting.jetpackpro.utils.plus

const val PAGE_SIZE = 10

interface FavoriteTheaterViewModel {
    fun getFavoriteTheater()
}

class FavoriteTheaterViewModelImpl(
    private val useCase: TheaterUseCaseMapper
) : FavoriteTheaterViewModel, TheaterBaseViewModel() {

    private val _state = MutableLiveData<FavoriteTheaterState>()
    val state: LiveData<FavoriteTheaterState>
        get() = _state

    override fun getFavoriteTheater() {
        addDisposable plus useCase.getAllTheaterFavorite()
            .doOnSubscribe { _state.value = FavoriteTheaterState.ShowLoading }
            .doAfterTerminate {
                _state.value = FavoriteTheaterState.HideLoading
                IdleResources.idleResources = IdleResources.DECREMENT_IDLE_RESOURCES
            }
            .subscribe({
                if (it.isEmpty())
                    _state.value = FavoriteTheaterState.LoadEmptyScreen
                else
                    _state.value = FavoriteTheaterState.LoadFavoriteTheater(it)
            }, {
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