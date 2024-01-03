package com.vinmahob.domain.productdetails.model

/**
 * the only model type the Domain knows about is the Domain model
 */
data class ProductDetailsDomainModel(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)
