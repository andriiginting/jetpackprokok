package com.andriiginting.jetpackpro.presentation.tv.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andriiginting.jetpackpro.BuildConfig
import com.andriiginting.jetpackpro.base.BaseViewModel
import com.andriiginting.jetpackpro.data.model.TvResponse
import com.andriiginting.jetpackpro.data.network.DicodingClient
import com.andriiginting.jetpackpro.data.network.DicodingService
import com.andriiginting.jetpackpro.utils.plus
import com.andriiginting.jetpackpro.utils.singleIo

interface TvShowContract {
    fun getTvShow()
}

class TvShowViewModel: BaseViewModel(), TvShowContract {

    private val network by lazy {
        DicodingClient
            .getRetrofitClient()
            ?.create(DicodingService::class.java)
    }

    private val _state = MutableLiveData<TvState>()
    val state: LiveData<TvState>
        get() = _state

    override fun getTvShow() {
        addDisposable plus network?.getPopularTvShow(BuildConfig.API_KEY)
            ?.doOnSubscribe { _state.postValue(TvState.ShowLoading) }
            ?.compose(singleIo())
            ?.subscribe({
                _state.value = TvState.HideLoading
                _state.postValue(TvState.LoadMovieSuccess(it))
            }, {
                _state.value = TvState.HideLoading
                _state.value = TvState.LoadMovieError
                Log.e("data-error", it.message.toString())
            })
    }
}

sealed class TvState {
    object ShowLoading : TvState()
    object HideLoading : TvState()
    object LoadMovieError : TvState()

    data class LoadMovieSuccess(val data: TvResponse) : TvState()
}