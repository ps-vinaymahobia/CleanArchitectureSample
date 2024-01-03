package com.vinmahob.datasource.productdetails.mapper

import com.vinmahob.data.productdetails.model.ProductDetailsDataModel
import com.vinmahob.datasource.productdetails.model.ProductResponseModel

class ProductDataSourceToDataModelMapper {
    fun toData(input: ProductResponseModel) = ProductDetailsDataModel(
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