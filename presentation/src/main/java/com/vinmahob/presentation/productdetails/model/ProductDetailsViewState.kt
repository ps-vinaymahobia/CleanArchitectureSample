package com.vinmahob.presentation.productdetails.model

//ToDo - remove import
import com.vinmahob.presentation.architecture.viewmodel.base.ViewState

sealed interface ProductDetailsViewState : ViewState {
    data object Loading : ProductDetailsViewState
    data object Idle : ProductDetailsViewState

    data class ProductDetailsLoaded(
        val productDetails: ProductDetailsPresentationModel
    ) : ProductDetailsViewState

    data class Error(val error: String?) : ProductDetailsViewState
}