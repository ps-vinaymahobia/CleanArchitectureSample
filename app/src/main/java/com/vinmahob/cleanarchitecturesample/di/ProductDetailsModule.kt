package com.vinmahob.cleanarchitecturesample.di

import com.vinmahob.data.productdetails.datasource.ProductDetailsDataSource
import com.vinmahob.data.productdetails.mapper.ProductDetailsDataToDomainMapper
import com.vinmahob.data.productdetails.repository.ProductDetailsLiveRepository
import com.vinmahob.data.productdetails.datasource.ProductDetailsLiveDataSource
import com.vinmahob.data.productdetails.network.ProductDetailsApiService
import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.productdetails.repository.ProductDetailsRepository
import com.vinmahob.domain.productdetails.usecase.GetProductDetailsUseCase
import com.vinmahob.presentation.productdetails.mapper.ProductDetailsDomainToPresentationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ProductDetailsModule {
    @Provides
    fun provideGetProductDetailsUseCase(
        productDetailsRepository: ProductDetailsRepository,
        contextProvider: CoroutineContextProvider
    ) = GetProductDetailsUseCase(productDetailsRepository, contextProvider)

    @Provides
    fun provideProductDetailsRepository(
        productDataSource: ProductDetailsDataSource,
        productDataToDomainMapper: ProductDetailsDataToDomainMapper
    ): ProductDetailsRepository =
        ProductDetailsLiveRepository(productDataSource, productDataToDomainMapper)

    @Provides
    fun provideProductDataSource(
        apiService: ProductDetailsApiService,
    ): ProductDetailsDataSource =
        ProductDetailsLiveDataSource(apiService)

    @Provides
    fun provideProductDataToDomainMapper() = ProductDetailsDataToDomainMapper()

    @Provides
    fun provideProductDetailsApiService(retrofit: Retrofit): ProductDetailsApiService {
        return retrofit.create(ProductDetailsApiService::class.java)
    }

    @Provides
    fun provideProductDetailsDomainToPresentationMapper() =
        ProductDetailsDomainToPresentationMapper()
}