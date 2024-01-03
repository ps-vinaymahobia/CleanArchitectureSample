package com.vinmahob.cleanarchitecturesample.r.di

import com.vinmahob.data.productlist.datasource.ProductListDataSource
import com.vinmahob.data.productlist.mapper.ProductListDataToDomainMapper
import com.vinmahob.data.productlist.mapper.ProductListItemDataToDomainMapper
import com.vinmahob.data.productlist.repository.ProductListLiveRepository
import com.vinmahob.datasource.productlist.datasource.ProductListLiveDataSource
import com.vinmahob.datasource.productlist.mapper.ProductListDataSourceToDataMapper
import com.vinmahob.datasource.productlist.mapper.ProductListItemDataSourceToDataMapper
import com.vinmahob.datasource.productlist.network.ProductListApiService
import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.productlist.repository.ProductListRepository
import com.vinmahob.domain.productlist.usecase.GetProductListUseCase
import com.vinmahob.presentation.productlist.mapper.ProductListDomainToPresentationMapper
import com.vinmahob.presentation.productlist.mapper.ProductListItemDomainToPresentationMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ProductListModule {
    @Provides
    fun provideGetProductListUseCase(
        productListRepository: ProductListRepository,
        contextProvider: CoroutineContextProvider
    ) = GetProductListUseCase(productListRepository, contextProvider)

    @Provides
    fun provideProductListRepository(
        productDataSource: ProductListDataSource,
        productDataToDomainMapper: ProductListDataToDomainMapper
    ): ProductListRepository =
        ProductListLiveRepository(productDataSource, productDataToDomainMapper)

    @Provides
    fun provideProductDataSource(
        apiService: ProductListApiService,
        productListDataSourceToDataMapper: ProductListDataSourceToDataMapper
    ): ProductListDataSource =
        ProductListLiveDataSource(apiService, productListDataSourceToDataMapper)

    @Provides
    fun provideProductListResponseToDataMapper(
        productListItemDataSourceToDataMapper: ProductListItemDataSourceToDataMapper
    ): ProductListDataSourceToDataMapper =
        ProductListDataSourceToDataMapper(productListItemDataSourceToDataMapper)

    @Provides
    fun provideProductListItemDataSourceToDataMapper(): ProductListItemDataSourceToDataMapper =
        ProductListItemDataSourceToDataMapper()

    @Provides
    fun provideProductDataToDomainMapper(
        productListItemDataToDomainMapper: ProductListItemDataToDomainMapper
    ) = ProductListDataToDomainMapper(productListItemDataToDomainMapper)

    @Provides
    fun provideProductListItemDataToDomainMapper() = ProductListItemDataToDomainMapper()

    @Provides
    fun provideProductListDomainToPresentationMapper(
        productListItemDomainToPresentationMapper : ProductListItemDomainToPresentationMapper
    ) = ProductListDomainToPresentationMapper(productListItemDomainToPresentationMapper)

    @Provides
    fun provideProductListItemDomainToPresentationMapper() = ProductListItemDomainToPresentationMapper()

    @Provides
    fun provideProductDetailsApiService(retrofit: Retrofit): ProductListApiService {
        return retrofit.create(ProductListApiService::class.java)
    }

}