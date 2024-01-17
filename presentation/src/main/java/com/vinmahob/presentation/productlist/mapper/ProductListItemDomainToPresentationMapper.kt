package com.vinmahob.presentation.productlist.mapper

import com.vinmahob.domain.productlist.model.ProductListItemDomainModel
import com.vinmahob.presentation.productlist.model.ProductListItemPresentationModel

class ProductListItemDomainToPresentationMapper {
    fun toPresentation(input: ProductListItemDomainModel) = ProductListItemPresentationModel(
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