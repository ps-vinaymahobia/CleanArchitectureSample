package com.vinmahob.data.productlist.network

import com.vinmahob.data.productlist.model.ProductListDataModel
import retrofit2.http.GET

interface ProductListApiService {
    @GET("/products")
    suspend fun getProductList(): ProductListDataModel

}