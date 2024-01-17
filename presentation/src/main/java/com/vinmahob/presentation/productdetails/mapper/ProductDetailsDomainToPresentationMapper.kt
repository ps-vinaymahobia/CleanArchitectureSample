package com.vinmahob.presentation.productdetails.mapper

import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel
import com.vinmahob.presentation.productdetails.model.ProductDetailsPresentationModel

class ProductDetailsDomainToPresentationMapper {
    fun toPresentation(input: ProductDetailsDomainModel) = ProductDetailsPresentationModel(
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