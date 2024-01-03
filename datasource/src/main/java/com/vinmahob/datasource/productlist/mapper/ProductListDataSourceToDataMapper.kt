package com.vinmahob.datasource.productlist.mapper

import com.vinmahob.data.productlist.model.ProductListDataModel
import com.vinmahob.datasource.productlist.model.ProductListResponseModel

class ProductListDataSourceToDataMapper(
    private val productListItemResponseToDataMapper: ProductListItemDataSourceToDataMapper
) {
    fun toData(input: ProductListResponseModel): ProductListDataModel {
        return ProductListDataModel(
            productList = input.products.map { item ->
                productListItemResponseToDataMapper.toData(
                    item
                )
            }
        )
    }
}