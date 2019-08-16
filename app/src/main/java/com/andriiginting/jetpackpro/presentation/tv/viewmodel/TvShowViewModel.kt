package com.andriiginting.jetpackpro.presentation.tv.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andriiginting.jetpackpro.base.BaseViewModel
import com.andriiginting.jetpackpro.data.model.TvResponse
import com.andriiginting.jetpackpro.data.repository.HomeRepositoryContract
import com.andriiginting.jetpackpro.presentation.tv.TvShowFragment
import com.andriiginting.jetpackpro.utils.IdleResources
import com.andriiginting.jetpackpro.utils.IdleResources.idleResources
import com.andriiginting.jetpackpro.utils.plus
import com.andriiginting.jetpackpro.utils.singleIo

interface TvShowContract {
    fun getTvShow()
}

class TvShowViewModel(
    private val repositoryContract: HomeRepositoryContract
) : BaseViewModel(), TvShowContract {

    private val _state = MutableLiveData<TvState>()
    val state: LiveData<TvState>
        get() = _state

    override fun getTvShow() {
        addDisposable plus repositoryContract.getTvShowMovie()
            .doOnSubscribe { _state.postValue(TvState.ShowLoading) }
            ?.doAfterTerminate { idleResources = IdleResources.DECREMENT_IDLE_RESOURCES }
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