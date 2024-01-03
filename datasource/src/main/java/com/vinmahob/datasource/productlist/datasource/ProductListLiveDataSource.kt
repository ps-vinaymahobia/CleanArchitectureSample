package com.vinmahob.datasource.productlist.datasource

import com.vinmahob.data.productlist.datasource.ProductListDataSource
import com.vinmahob.data.productlist.model.ProductListDataModel
import com.vinmahob.datasource.productlist.mapper.ProductListDataSourceToDataMapper
import com.vinmahob.datasource.productlist.network.ProductListApiService


class ProductListLiveDataSource(
    private val productListApiService: ProductListApiService,
    private val productListResponseToDataMapper: ProductListDataSourceToDataMapper
) : ProductListDataSource {
    override suspend fun getProductList(): ProductListDataModel {
        return productListResponseToDataMapper.toData(productListApiService.getProductList())
    }
}