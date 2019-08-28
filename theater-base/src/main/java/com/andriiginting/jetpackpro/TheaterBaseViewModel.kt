package com.andriiginting.jetpackpro

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class TheaterBaseViewModel : ViewModel() {
    val addDisposable by lazy { CompositeDisposable() }

    override fun onCleared() = addDisposable.clear()
}