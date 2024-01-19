package com.vinmahob.data.productdetails.repository

import com.vinmahob.data.productdetails.datasource.ProductDetailsDataSource
import com.vinmahob.data.productdetails.mapper.ProductDetailsDataToDomainMapper
import com.vinmahob.domain.architecture.model.UseCaseResult
import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel
import com.vinmahob.domain.productdetails.repository.ProductDetailsRepository

class ProductDetailsLiveRepository(
    private val productDetailsDataSource: ProductDetailsDataSource,
    private val productDetailsDataToDomainMapper: ProductDetailsDataToDomainMapper
) : ProductDetailsRepository {
    override suspend fun getProductDetails(productId: Int): UseCaseResult<ProductDetailsDomainModel> {
        return try {
            UseCaseResult.OnSuccess(
                productDetailsDataToDomainMapper.toDomain(
                    productDetailsDataSource.getProductDetails(productId)
                )
            )
        } catch (throwable: Throwable) {
            UseCaseResult.OnError(throwable)
        }
    }
}