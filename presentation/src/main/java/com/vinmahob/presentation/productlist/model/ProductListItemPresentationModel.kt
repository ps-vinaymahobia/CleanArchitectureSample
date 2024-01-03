package com.vinmahob.presentation.productlist.model

data class ProductListItemPresentationModel(
    val id: Int,
    val title: String,
    val price: Int,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
)
