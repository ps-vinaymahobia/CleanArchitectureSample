package com.vinmahob.data.productlist.datasource

import com.vinmahob.data.productlist.model.ProductListDataModel
import com.vinmahob.data.productlist.network.ProductListApiService


class ProductListLiveDataSource(
    private val productListApiService: ProductListApiService,
) : ProductListDataSource {
    override suspend fun getProductList(): ProductListDataModel {
        return productListApiService.getProductList()
    }
}