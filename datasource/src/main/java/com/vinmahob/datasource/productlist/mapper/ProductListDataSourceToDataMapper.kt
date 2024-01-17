package com.vinmahob.datasource.productlist.mapper

import com.vinmahob.data.productlist.model.ProductListDataModel
import com.vinmahob.datasource.productlist.model.ProductListDataSourceModel

class ProductListDataSourceToDataMapper(
    private val productListItemDataSourceToDataMapper: ProductListItemDataSourceToDataMapper
) {
    operator fun invoke(input: ProductListDataSourceModel): ProductListDataModel {
        return ProductListDataModel(
            productList = input.products.map { item ->
                productListItemDataSourceToDataMapper(item)
            }
        )
    }
}