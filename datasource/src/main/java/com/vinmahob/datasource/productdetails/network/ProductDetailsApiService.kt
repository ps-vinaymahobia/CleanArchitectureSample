package com.vinmahob.datasource.productdetails.network

import com.vinmahob.datasource.productdetails.model.ProductResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailsApiService {
    @GET("/products/{id}")
    suspend fun getProductDetail(@Path("id") id: Int): ProductResponseModel
}