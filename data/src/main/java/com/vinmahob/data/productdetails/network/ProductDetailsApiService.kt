package com.vinmahob.data.productdetails.network

import com.vinmahob.data.productdetails.model.ProductDetailsDataModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductDetailsApiService {
    @GET("/products/{id}")
    suspend fun getProductDetails(@Path("id") id: Int): ProductDetailsDataModel
}