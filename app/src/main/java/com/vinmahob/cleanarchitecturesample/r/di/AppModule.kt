package com.vinmahob.cleanarchitecturesample.r.di

import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.architecture.usecase.UseCaseExecutor
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://dummyjson.com"

    @Provides
    fun provideCoroutineContextProvider() = object : CoroutineContextProvider {
        override val main: CoroutineContext
            get() = Dispatchers.Main.immediate
        override val io: CoroutineContext
            get() = Dispatchers.IO

    }

    @Provides
    fun provideUseCaseExecutorProvider() =
        object : UseCaseExecutorProvider {
            override fun invoke(coroutineScope: CoroutineScope): UseCaseExecutor {
                return UseCaseExecutor(coroutineScope)
            }

        }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}