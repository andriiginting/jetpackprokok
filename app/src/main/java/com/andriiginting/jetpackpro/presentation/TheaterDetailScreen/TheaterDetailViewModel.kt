package com.andriiginting.jetpackpro.presentation.TheaterDetailScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andriiginting.jetpackpro.base.BaseViewModel
import com.andriiginting.jetpackpro.data.database.TheaterFavorite
import com.andriiginting.jetpackpro.data.model.MovieItem
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.data.repository.DetailScreenRepositoryContract
import com.andriiginting.jetpackpro.utils.IdleResources.DECREMENT_IDLE_RESOURCES
import com.andriiginting.jetpackpro.utils.IdleResources.idleResources
import com.andriiginting.jetpackpro.utils.completeIo
import com.andriiginting.jetpackpro.utils.plus
import com.andriiginting.jetpackpro.utils.singleIo

interface TheaterDetailContract {
    fun getSimilarMovie(id: String)
    fun getSimilarTv(id: String)
    fun saveFavoriteTheater(data: MovieItem, type: String)
    fun isFavoriteTheater(id: String)
    fun deleteTheaterData(movieId: String)
}

class TheaterDetailViewModel(
    private val repository: DetailScreenRepositoryContract
) : BaseViewModel(), TheaterDetailContract {

    private val _state = MutableLiveData<DetailScreenState>()
    val state: LiveData<DetailScreenState>
        get() = _state

    override fun getSimilarMovie(id: String) {
        addDisposable plus repository.getSimilarMovie(id)
            .doOnSubscribe { _state.postValue(DetailScreenState.ShowLoading) }
            ?.doAfterTerminate { idleResources = DECREMENT_IDLE_RESOURCES }
            ?.compose(singleIo())
            ?.subscribe({
                _state.value = DetailScreenState.HideLoading
                _state.postValue(DetailScreenState.LoadSimilarMovieSuccess(it))
            }, {
                _state.value = DetailScreenState.HideLoading
                _state.value = DetailScreenState.LoadScreenError
                Log.e("data-error", it.message.toString())
            })
    }

    override fun getSimilarTv(id: String) {
        addDisposable plus repository.getSimilarTvShow(id)
            .doOnSubscribe { _state.postValue(DetailScreenState.ShowLoading) }
            ?.doAfterTerminate { idleResources = DECREMENT_IDLE_RESOURCES }
            ?.compose(singleIo())
            ?.subscribe({
                _state.value = DetailScreenState.HideLoading
                _state.postValue(DetailScreenState.LoadSimilarTVSuccess(it))
            }, {
                _state.value = DetailScreenState.HideLoading
                _state.value = DetailScreenState.LoadScreenError
                Log.e("data-error", it.message.toString())
            })
    }

    override fun saveFavoriteTheater(data: MovieItem, type: String) {
        addDisposable plus repository.saveToDatabase(data, type)
            .doAfterTerminate { idleResources = DECREMENT_IDLE_RESOURCES }
            .compose(completeIo())
            .subscribe({
                _state.value = DetailScreenState.SuccessToSaveData(data)
            }, {
                _state.value = DetailScreenState.FailedToSaveData
            })
    }

    override fun isFavoriteTheater(id: String) {
        addDisposable plus repository.isFavoriteTheater(id)
            .doAfterTerminate { idleResources = DECREMENT_IDLE_RESOURCES }
            .compose(singleIo())
            .subscribe({
                if (it != null) {
                    _state.value = DetailScreenState.IsFavoriteTheater(true)
                }
            }, {
                _state.value = DetailScreenState.IsFavoriteTheater(false)
            })
    }

    override fun deleteTheaterData(movieId: String) {
        addDisposable plus repository.deleteTheaterData(movieId)
            .doAfterTerminate { idleResources = DECREMENT_IDLE_RESOURCES }
            .compose(completeIo())
            .subscribe({
                _state.value = DetailScreenState.SuccessDeleteTheater
            }, {
                _state.value = DetailScreenState.FailedDeleteTheater
            })
    }
}

sealed class DetailScreenState {
    object ShowLoading : DetailScreenState()
    object HideLoading : DetailScreenState()
    object LoadScreenError : DetailScreenState()
    object SuccessDeleteTheater : DetailScreenState()
    object FailedDeleteTheater : DetailScreenState()
    object FailedToSaveData : DetailScreenState()

    data class IsFavoriteTheater(val status: Boolean) : DetailScreenState()
    data class SuccessToSaveData(val data: MovieItem) : DetailScreenState()
    data class LoadSimilarMovieSuccess(val data: MovieResponse) : DetailScreenState()
    data class LoadSimilarTVSuccess(val data: MovieResponse) : DetailScreenState()
}