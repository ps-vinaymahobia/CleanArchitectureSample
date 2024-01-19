package com.vinmahob.data.productlist.repository

import com.vinmahob.data.productlist.datasource.ProductListDataSource
import com.vinmahob.data.productlist.mapper.ProductListDataToDomainMapper
import com.vinmahob.domain.architecture.model.UseCaseResult
import com.vinmahob.domain.productlist.model.ProductListDomainModel
import com.vinmahob.domain.productlist.repository.ProductListRepository

class ProductListLiveRepository(
    private val productListDataSource: ProductListDataSource,
    private val productListDataToDomainMapper: ProductListDataToDomainMapper
) : ProductListRepository {
    override suspend fun getProductList(): UseCaseResult<ProductListDomainModel> {
        return try {
            UseCaseResult.OnSuccess(productListDataToDomainMapper.toDomain(productListDataSource.getProductList()))
        } catch (throwable: Throwable) {
            UseCaseResult.OnError(throwable)
        }
    }
}