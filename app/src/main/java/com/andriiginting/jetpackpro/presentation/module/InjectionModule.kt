package com.andriiginting.jetpackpro.presentation.module

import android.content.Context
import com.andriiginting.jetpackpro.data.database.TheaterDAO
import com.andriiginting.jetpackpro.data.database.TheaterDatabase
import com.andriiginting.jetpackpro.data.network.DicodingClient
import com.andriiginting.jetpackpro.data.network.DicodingService
import com.andriiginting.jetpackpro.data.repository.DetailScreenRepository
import com.andriiginting.jetpackpro.data.repository.FavoriteRepository
import com.andriiginting.jetpackpro.data.repository.FavoriteRepositoryImpl
import com.andriiginting.jetpackpro.data.repository.HomeRepository
import com.andriiginting.jetpackpro.domain.TheaterUseCase
import com.andriiginting.jetpackpro.domain.TheaterUseCaseMapper

object InjectionModule {
    fun provideTheaterDatabase(context: Context): DetailScreenRepository {
        val database = TheaterDatabase.getInstance(context)
        return DetailScreenRepository(provideNetworkService(), database!!.theaterDAO())
    }

    fun provideHomeRepository(): HomeRepository {
        return HomeRepository(provideNetworkService())
    }

    fun provideFavoriteUseCase(context: Context): TheaterUseCaseMapper {
        return TheaterUseCase(FavoriteRepositoryImpl(provideDatabase(context)))
    }

    private fun provideNetworkService(): DicodingService = DicodingClient
        .getRetrofitClient()?.create(DicodingService::class.java)!!

    private fun provideDatabase(context: Context): TheaterDAO {
        return TheaterDatabase.getInstance(context = context)?.theaterDAO()!!
    }
}