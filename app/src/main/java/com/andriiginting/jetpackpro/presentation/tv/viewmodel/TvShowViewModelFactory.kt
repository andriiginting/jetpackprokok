package com.andriiginting.jetpackpro.presentation.tv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andriiginting.jetpackpro.data.repository.HomeRepositoryContract

@Suppress("UNCHECKED_CAST")
class TvShowViewModelFactory(
    private val repositoryContract: HomeRepositoryContract
): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = TvShowViewModel(repositoryContract) as T
}