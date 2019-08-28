package com.andriiginting.jetpackpro.presentation.movie.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.andriiginting.jetpackpro.TheaterBaseViewModel
import com.andriiginting.jetpackpro.data.model.MovieResponse
import com.andriiginting.jetpackpro.data.repository.HomeRepositoryContract
import com.andriiginting.jetpackpro.utils.IdleResources.DECREMENT_IDLE_RESOURCES
import com.andriiginting.jetpackpro.utils.IdleResources.idleResources
import com.andriiginting.jetpackpro.utils.plus
import com.andriiginting.jetpackpro.utils.singleIo

interface MovieContract {
    fun getMovies()
}

class MovieViewModel(
    private val repositoryContract: HomeRepositoryContract
) : TheaterBaseViewModel(), MovieContract {

    private val _state = MutableLiveData<MovieState>()
    val state: LiveData<MovieState>
        get() = _state

    override fun getMovies() {
        addDisposable plus repositoryContract.getPopularMovies()
            .doOnSubscribe { _state.postValue(MovieState.ShowLoading) }
            ?.doAfterTerminate { idleResources = DECREMENT_IDLE_RESOURCES }
            ?.compose(singleIo())
            ?.subscribe({
                _state.value = MovieState.HideLoading
                _state.postValue(MovieState.LoadMovieSuccess(it))
            }, {
                _state.value = MovieState.HideLoading
                _state.value = MovieState.LoadMovieError
                Log.e("data-error", it.message.toString())
            })
    }
}

sealed class MovieState {
    object ShowLoading : MovieState()
    object HideLoading : MovieState()
    object LoadMovieError : MovieState()

    data class LoadMovieSuccess(val data: MovieResponse) : MovieState()
}