package com.vinmahob.data.productdetails.datasource

import com.vinmahob.data.productdetails.model.ProductDetailsDataModel


interface ProductDetailsDataSource {
    suspend fun getProductDetails(productId: Int) : ProductDetailsDataModel
}