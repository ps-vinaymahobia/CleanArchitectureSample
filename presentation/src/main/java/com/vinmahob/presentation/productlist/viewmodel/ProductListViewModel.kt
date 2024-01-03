package com.vinmahob.presentation.productlist.viewmodel

import com.vinmahob.domain.productlist.model.ProductListDomainModel
import com.vinmahob.domain.productlist.usecase.GetProductListUseCase
import com.vinmahob.presentation.architecture.viewmodel.base.BaseViewModel
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.productlist.mapper.ProductListDomainToPresentationMapper
import com.vinmahob.presentation.productlist.model.ProductListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val productListDomainToPresentationMapper: ProductListDomainToPresentationMapper,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ProductListViewState>(useCaseExecutorProvider = useCaseExecutorProvider) {
    override fun initialState() = ProductListViewState.Idle

    init {
        fetchProductList()
    }

    private fun fetchProductList() {
        useCaseExecutor.execute(
            getProductListUseCase, null, ::currentProductList
        )
    }

    private fun currentProductList(productList: ProductListDomainModel) {
        val productDetails = productListDomainToPresentationMapper.toPresentation(productList)
        updateViewState { ProductListViewState.ProductListLoaded(productList = productDetails) }
    }
}