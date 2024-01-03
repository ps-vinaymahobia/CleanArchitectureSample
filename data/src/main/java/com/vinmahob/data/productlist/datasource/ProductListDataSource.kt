package com.vinmahob.data.productlist.datasource

import com.vinmahob.data.productlist.model.ProductListDataModel


interface ProductListDataSource {
    suspend fun getProductList() : ProductListDataModel
}