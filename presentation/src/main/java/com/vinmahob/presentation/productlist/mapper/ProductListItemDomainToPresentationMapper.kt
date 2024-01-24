package com.vinmahob.presentation.productlist.mapper

import com.vinmahob.domain.productlist.model.ProductListItemDomainModel
import com.vinmahob.presentation.productlist.model.ProductListItemPresentationModel

class ProductListItemDomainToPresentationMapper {
    fun toPresentation(input: ProductListItemDomainModel) = with(input) {
        ProductListItemPresentationModel(
            id = id,
            title = title,
            thumbnail = thumbnail,
        )
    }
}