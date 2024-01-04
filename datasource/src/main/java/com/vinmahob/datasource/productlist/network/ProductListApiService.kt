package com.vinmahob.datasource.productlist.network

import com.vinmahob.datasource.productlist.model.ProductListDataSourceModel
import retrofit2.http.GET

interface ProductListApiService {
    @GET("/products")
    suspend fun getProductList(): ProductListDataSourceModel

}