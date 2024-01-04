package com.vinmahob.datasource.productlist.mapper

import com.vinmahob.data.productlist.model.ProductListDataModel
import com.vinmahob.datasource.productlist.model.ProductListDataSourceModel

class ProductListDataSourceToDataMapper(
    private val productListItemDataSourceToDataMapper: ProductListItemDataSourceToDataMapper
) {
    fun toData(input: ProductListDataSourceModel): ProductListDataModel {
        return ProductListDataModel(
            productList = input.products.map { item ->
                productListItemDataSourceToDataMapper.toData(
                    item
                )
            }
        )
    }
}