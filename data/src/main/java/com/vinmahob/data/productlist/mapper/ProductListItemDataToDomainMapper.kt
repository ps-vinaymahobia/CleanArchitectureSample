package com.vinmahob.data.productlist.mapper

import com.vinmahob.data.productlist.model.ProductListItemDataModel
import com.vinmahob.domain.productlist.model.ProductListItemDomainModel

class ProductListItemDataToDomainMapper {
    operator fun invoke(input: ProductListItemDataModel) = ProductListItemDomainModel(
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