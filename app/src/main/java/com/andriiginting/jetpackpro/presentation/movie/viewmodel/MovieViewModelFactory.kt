package com.andriiginting.jetpackpro.presentation.movie.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andriiginting.jetpackpro.data.repository.HomeRepositoryContract

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(
    private val repositoryContract: HomeRepositoryContract
): ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MovieViewModel(repositoryContract) as T
}