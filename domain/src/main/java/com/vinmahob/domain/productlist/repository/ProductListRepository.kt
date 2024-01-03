package com.vinmahob.domain.productlist.repository

import com.vinmahob.domain.productlist.model.ProductListDomainModel


interface ProductListRepository {
    suspend fun getProductList() : ProductListDomainModel
}