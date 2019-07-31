package com.andriiginting.jetpackpro.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    val addDisposable by lazy { CompositeDisposable() }

    override fun onCleared() = addDisposable.clear()
}