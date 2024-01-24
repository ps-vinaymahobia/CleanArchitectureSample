package com.vinmahob.presentation.productdetails.model

data class ProductDetailsPresentationModel(
    val id: Int,
    val title: String,
    val description: String,
    val images: List<String>
)
