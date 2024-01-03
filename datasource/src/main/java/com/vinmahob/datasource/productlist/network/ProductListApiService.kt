package com.vinmahob.datasource.productlist.network

import com.vinmahob.datasource.productlist.model.ProductListResponseModel
import retrofit2.http.GET

interface ProductListApiService {
    @GET("/products")
    suspend fun getProductList(): ProductListResponseModel

}