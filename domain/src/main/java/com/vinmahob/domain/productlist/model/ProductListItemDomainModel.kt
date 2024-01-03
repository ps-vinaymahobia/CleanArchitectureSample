package com.vinmahob.domain.productlist.model

data class ProductListItemDomainModel(
    val id: Int,
    val title: String,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
)
