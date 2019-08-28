package com.andriiginting.jetpackpro.data.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DicodingServiceModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient{
        return OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(createLoggingInterceptor())
            .addInterceptor(defaultHTTPClient())
            .pingInterval(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .connectTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideDicodingApiService(retrofit: Retrofit): DicodingService = retrofit.create(DicodingService::class.java)

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Throws(IOException::class)
    private fun defaultHTTPClient(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .build()
            return@Interceptor chain.proceed(request)
        }
    }
}