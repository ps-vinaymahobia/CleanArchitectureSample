package com.vinmahob.domain.productlist.repository

import com.vinmahob.domain.architecture.model.UseCaseResult
import com.vinmahob.domain.productlist.model.ProductListDomainModel


interface ProductListRepository {
    suspend fun getProductList(): UseCaseResult<ProductListDomainModel>
}