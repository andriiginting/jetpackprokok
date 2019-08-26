package com.andriiginting.jetpackpro.presentation.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andriiginting.jetpackpro.data.repository.FavoriteRepository

@Suppress("UNCHECKED_CAST")
class FavoriteViewModelFactory(
    private val repository: FavoriteRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = FavoriteTheaterViewModelImpl(repository) as T
}