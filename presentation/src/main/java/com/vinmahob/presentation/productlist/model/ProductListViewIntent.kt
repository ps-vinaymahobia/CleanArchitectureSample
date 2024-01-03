package com.vinmahob.presentation.productlist.model

sealed interface ProductListViewIntent {
    data object LoadProductList : ProductListViewIntent
}