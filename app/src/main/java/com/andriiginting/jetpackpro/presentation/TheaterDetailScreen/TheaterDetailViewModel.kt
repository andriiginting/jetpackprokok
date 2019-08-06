package com.andriiginting.jetpackpro.presentation.TheaterDetailScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andriiginting.jetpackpro.BuildConfig
import com.andriiginting.jetpackpro.base.BaseViewModel
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.data.model.TvResponse
import com.andriiginting.jetpackpro.data.network.DicodingClient
import com.andriiginting.jetpackpro.data.network.DicodingService
import com.andriiginting.jetpackpro.utils.plus
import com.andriiginting.jetpackpro.utils.singleIo

interface TheaterDetailContract {
    fun getSimilarMovie(id: String)
    fun getSimilarTv(id: String)
}

class TheaterDetailViewModel : BaseViewModel(), TheaterDetailContract {

    private val network by lazy {
        DicodingClient
            .getRetrofitClient()
            ?.create(DicodingService::class.java)
    }

    private val _state = MutableLiveData<DetailScreenState>()
    val state: LiveData<DetailScreenState>
        get() = _state

    override fun getSimilarMovie(id: String) {
        addDisposable plus network?.getSimilarMovie(id, BuildConfig.API_KEY)
            ?.doOnSubscribe { _state.postValue(DetailScreenState.ShowLoading) }
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
        addDisposable plus network?.getSimilarTvShow(id, BuildConfig.API_KEY)
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