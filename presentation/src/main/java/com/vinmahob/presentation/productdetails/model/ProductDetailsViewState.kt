package com.vinmahob.presentation.productdetails.model

import com.vinmahob.presentation.architecture.model.ErrorPresentationModel

sealed interface ProductDetailsViewState {
    data object Loading : ProductDetailsViewState
    data object Idle : ProductDetailsViewState

    data class ProductDetailsLoaded(
        val productDetails : ProductDetailsPresentationModel
    ) : ProductDetailsViewState

    data class Error(val error : ErrorPresentationModel) : ProductDetailsViewState
}