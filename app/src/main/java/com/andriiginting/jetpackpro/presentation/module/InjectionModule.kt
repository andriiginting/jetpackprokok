package com.andriiginting.jetpackpro.presentation.module

import android.content.Context
import com.andriiginting.jetpackpro.data.database.TheaterDatabase
import com.andriiginting.jetpackpro.data.network.DicodingClient
import com.andriiginting.jetpackpro.data.network.DicodingService
import com.andriiginting.jetpackpro.data.repository.DetailScreenRepository
import com.andriiginting.jetpackpro.data.repository.HomeRepository

object InjectionModule {
    fun provideTheaterDatabase(context: Context): DetailScreenRepository {
        val database = TheaterDatabase.getInstance(context)
        return DetailScreenRepository(provideNetworkService(), database!!.theaterDAO())
    }

    fun provideHomeRepository(): HomeRepository {
        return HomeRepository(provideNetworkService())
    }

    private fun provideNetworkService(): DicodingService = DicodingClient
        .getRetrofitClient()?.create(DicodingService::class.java)!!
}