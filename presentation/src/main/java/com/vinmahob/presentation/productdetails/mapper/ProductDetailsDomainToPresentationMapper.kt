package com.vinmahob.presentation.productdetails.mapper

import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel
import com.vinmahob.presentation.productdetails.model.ProductDetailsPresentationModel

class ProductDetailsDomainToPresentationMapper {
    fun toPresentation(input: ProductDetailsDomainModel) = with(input) {
        ProductDetailsPresentationModel(
            id = id,
            title = title,
            description = description,
            images = images
        )
    }
}