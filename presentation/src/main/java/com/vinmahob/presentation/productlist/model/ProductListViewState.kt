package com.vinmahob.presentation.productlist.model

import com.vinmahob.presentation.architecture.viewmodel.base.ViewState

sealed interface ProductListViewState : ViewState {
    data object Loading : ProductListViewState
    data object Idle : ProductListViewState

    data class ProductListLoaded(
        val productList: ProductListPresentationModel
    ) : ProductListViewState

    data class Error(val error: String?) : ProductListViewState
}