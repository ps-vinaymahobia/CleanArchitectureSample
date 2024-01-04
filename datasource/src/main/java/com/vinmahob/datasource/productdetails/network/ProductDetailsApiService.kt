package com.vinmahob.datasource.productdetails.network

import com.vinmahob.datasource.productdetails.model.ProductDetailsDataSourceModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailsApiService {
    @GET("/products/{id}")
    suspend fun getProductDetails(@Path("id") id: Int): ProductDetailsDataSourceModel
}