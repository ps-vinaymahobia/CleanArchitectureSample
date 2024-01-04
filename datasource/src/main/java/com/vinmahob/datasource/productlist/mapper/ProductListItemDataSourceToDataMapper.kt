package com.vinmahob.datasource.productlist.mapper

import com.vinmahob.data.productlist.model.ProductListItemDataModel
import com.vinmahob.datasource.productlist.model.ProductListItemDataSourceModel

class ProductListItemDataSourceToDataMapper {
    fun toData(input: ProductListItemDataSourceModel) = ProductListItemDataModel(
        id = input.id,
        title = input.title,
        description = input.description,
        price = input.price,
        discountPercentage = input.discountPercentage,
        rating = input.rating,
        stock = input.stock,
        brand = input.brand,
        category = input.category,
        thumbnail = input.thumbnail,
        images = input.images
    )
}