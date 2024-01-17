package com.vinmahob.presentation.productdetails.model

import com.vinmahob.presentation.architecture.viewmodel.base.ViewIntent

sealed interface ProductDetailsViewIntent : ViewIntent {
    data object LoadSelectedProductDetails : ProductDetailsViewIntent
}