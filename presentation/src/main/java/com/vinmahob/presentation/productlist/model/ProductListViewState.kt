package com.vinmahob.presentation.productlist.model

import com.vinmahob.presentation.architecture.model.ErrorPresentationModel

sealed interface ProductListViewState {
    data object Loading : ProductListViewState
    data object Idle : ProductListViewState

    data class ProductListLoaded(
        val productList : ProductListPresentationModel
    ) : ProductListViewState

    data class Error(val error : ErrorPresentationModel) : ProductListViewState
}