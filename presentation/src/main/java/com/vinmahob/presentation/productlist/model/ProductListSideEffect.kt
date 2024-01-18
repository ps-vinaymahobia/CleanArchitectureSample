package com.vinmahob.presentation.productlist.model

import com.vinmahob.presentation.architecture.viewmodel.base.SideEffect

sealed interface ProductListSideEffect : SideEffect{
    data class NavigateToProductDetails(val id: Int) : ProductListSideEffect
}
