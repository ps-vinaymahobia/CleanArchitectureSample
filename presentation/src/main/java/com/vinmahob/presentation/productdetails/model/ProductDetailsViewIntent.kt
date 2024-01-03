package com.vinmahob.presentation.productdetails.model

sealed interface ProductDetailsViewIntent {
    data object LoadSelectedProductDetails : ProductDetailsViewIntent
}