package com.vinmahob.data.productdetails.mapper

import com.vinmahob.data.productdetails.model.ProductDetailsDataModel
import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel

class ProductDetailsDataToDomainMapper {
    operator fun invoke(input: ProductDetailsDataModel) = ProductDetailsDomainModel(
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