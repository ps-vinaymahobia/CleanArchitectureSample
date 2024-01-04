package com.vinmahob.presentation.productlist.viewmodel

import com.vinmahob.domain.productlist.model.ProductListDomainModel
import com.vinmahob.domain.productlist.usecase.GetProductListUseCase
import com.vinmahob.presentation.architecture.viewmodel.base.BaseViewModel
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.productlist.mapper.ProductListDomainToPresentationMapper
import com.vinmahob.presentation.productlist.model.ProductListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jetbrains.annotations.VisibleForTesting
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

    fun fetchProductList() {
        useCaseExecutor.execute(
            getProductListUseCase, null, ::currentProductList
        )
    }

    @VisibleForTesting
    fun currentProductList(productList: ProductListDomainModel) {
        val productDetails = productListDomainToPresentationMapper.toPresentation(productList)
        updateViewState { ProductListViewState.ProductListLoaded(productList = productDetails) }
    }
}