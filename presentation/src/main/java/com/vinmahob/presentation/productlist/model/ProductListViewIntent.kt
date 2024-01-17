package com.vinmahob.presentation.productlist.model

import com.vinmahob.presentation.architecture.viewmodel.base.ViewIntent

sealed interface ProductListViewIntent : ViewIntent {
    data object LoadProductList : ProductListViewIntent
}