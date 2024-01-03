package com.vinmahob.presentation.productlist.mapper

import com.vinmahob.domain.productlist.model.ProductListDomainModel
import com.vinmahob.presentation.productlist.model.ProductListPresentationModel

class ProductListDomainToPresentationMapper(
    private val productListItemDomainToPresentationMapper: ProductListItemDomainToPresentationMapper
) {
    fun toPresentation(input: ProductListDomainModel): ProductListPresentationModel {
        return ProductListPresentationModel(
            productList = input.productList.map { item ->
                productListItemDomainToPresentationMapper.toPresentation(
                    item
                )
            }
        )
    }
}