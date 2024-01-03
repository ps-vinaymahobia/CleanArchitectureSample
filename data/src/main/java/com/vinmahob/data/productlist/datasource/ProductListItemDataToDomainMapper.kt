package com.vinmahob.data.productlist.datasource

import com.vinmahob.data.productlist.model.ProductListItemDataModel
import com.vinmahob.domain.productlist.model.ProductListItemDomainModel

class ProductListItemDataToDomainMapper {
    fun toDomain(input: ProductListItemDataModel) = ProductListItemDomainModel(
        id = input.id,
        title = input.title,
        price = input.price,
        rating = input.rating,
        stock = input.stock,
        brand = input.brand,
        category = input.category,
        thumbnail = input.thumbnail,
    )
}