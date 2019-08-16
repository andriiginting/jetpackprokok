package com.andriiginting.jetpackpro.presentation.TheaterDetailScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andriiginting.jetpackpro.base.BaseViewModel
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.data.repository.DetailScreenRepositoryContract
import com.andriiginting.jetpackpro.utils.IdleResources.DECREMENT_IDLE_RESOURCES
import com.andriiginting.jetpackpro.utils.IdleResources.idleResources
import com.andriiginting.jetpackpro.utils.plus
import com.andriiginting.jetpackpro.utils.singleIo

interface TheaterDetailContract {
    fun getSimilarMovie(id: String)
    fun getSimilarTv(id: String)
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
}

sealed class DetailScreenState {
    object ShowLoading : DetailScreenState()
    object HideLoading : DetailScreenState()
    object LoadScreenError : DetailScreenState()

    data class LoadSimilarMovieSuccess(val data: MovieResponse) : DetailScreenState()
    data class LoadSimilarTVSuccess(val data: MovieResponse) : DetailScreenState()
}