package com.vinmahob.data.productdetails.mapper

import com.vinmahob.data.productdetails.model.ProductDetailsDataModel
import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel

class ProductDetailsDataToDomainMapper {
    fun toDomain(input: ProductDetailsDataModel) = with(input) {
        ProductDetailsDomainModel(
            id = id,
            title = title,
            description = description,
            price = price,
            discountPercentage = discountPercentage,
            rating = rating,
            stock = stock,
            brand = brand,
            category = category,
            thumbnail = thumbnail,
            images = images
        )
    }
}