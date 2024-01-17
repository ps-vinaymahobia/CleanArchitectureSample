package com.vinmahob.data.productlist.mapper

import com.vinmahob.data.productlist.model.ProductListDataModel
import com.vinmahob.domain.productlist.model.ProductListDomainModel

class ProductListDataToDomainMapper(
    private val productListItemDataToDomainMapper: ProductListItemDataToDomainMapper
) {
    fun toDomain(input: ProductListDataModel): ProductListDomainModel {
        return ProductListDomainModel(
            productList = input.productList.map { item ->
                productListItemDataToDomainMapper.toDomain(
                    item
                )
            }
        )
    }
}