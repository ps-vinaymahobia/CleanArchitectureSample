package com.vinmahob.data.productlist.mapper

import com.vinmahob.data.productlist.model.ProductListItemDataModel
import com.vinmahob.domain.productlist.model.ProductListItemDomainModel

class ProductListItemDataToDomainMapper {
    fun toDomain(input: ProductListItemDataModel) = with(input) {
        ProductListItemDomainModel(
            id = id,
            title = title,
            price = price,
            rating = rating,
            stock = stock,
            brand = brand,
            category = category,
            thumbnail = thumbnail,
        )
    }
}