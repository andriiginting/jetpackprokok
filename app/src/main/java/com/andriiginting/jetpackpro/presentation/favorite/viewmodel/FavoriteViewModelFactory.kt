package com.andriiginting.jetpackpro.presentation.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andriiginting.jetpackpro.domain.TheaterUseCaseMapper

@Suppress("UNCHECKED_CAST")
class FavoriteViewModelFactory(
    private val useCaseMapper: TheaterUseCaseMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        FavoriteTheaterViewModelImpl(useCaseMapper) as T
}