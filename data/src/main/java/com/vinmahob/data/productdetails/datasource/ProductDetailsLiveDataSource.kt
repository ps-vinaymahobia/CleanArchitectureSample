package com.vinmahob.data.productdetails.datasource

import com.vinmahob.data.productdetails.model.ProductDetailsDataModel
import com.vinmahob.data.productdetails.network.ProductDetailsApiService

class ProductDetailsLiveDataSource(
    private val productDetailsApiService: ProductDetailsApiService,
) : ProductDetailsDataSource {
    override suspend fun getProductDetails(productId: Int): ProductDetailsDataModel {
        return productDetailsApiService.getProductDetails(productId)
    }
}