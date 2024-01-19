package com.vinmahob.domain.productdetails.repository

import com.vinmahob.domain.architecture.model.UseCaseResult
import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel

interface ProductDetailsRepository {
    suspend fun getProductDetails(productId: Int) : UseCaseResult<ProductDetailsDomainModel>
}