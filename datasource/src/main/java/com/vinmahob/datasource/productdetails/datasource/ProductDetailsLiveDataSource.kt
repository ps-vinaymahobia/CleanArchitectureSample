package com.vinmahob.datasource.productdetails.datasource

import com.vinmahob.data.productdetails.datasource.ProductDetailsDataSource
import com.vinmahob.data.productdetails.model.ProductDetailsDataModel
import com.vinmahob.datasource.productdetails.mapper.ProductDataSourceToDataModelMapper
import com.vinmahob.datasource.productdetails.network.ProductDetailsApiService

class ProductDetailsLiveDataSource (
    private val productApiService: ProductDetailsApiService,
    private val productResponseToDataModelMapper: ProductDataSourceToDataModelMapper
) : ProductDetailsDataSource {
    override suspend fun getProductDetails(productId: Int): ProductDetailsDataModel {
       return productResponseToDataModelMapper.toData(productApiService.getProductDetail(productId))
    }
}