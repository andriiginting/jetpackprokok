package com.andriiginting.jetpackpro.presentation.TheaterDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andriiginting.jetpackpro.data.repository.DetailScreenRepositoryContract

@Suppress("UNCHECKED_CAST")
class TheaterDetailViewModelFactory(
    private val repositoryContract: DetailScreenRepositoryContract
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = TheaterDetailViewModel(repositoryContract) as T
}