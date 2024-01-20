package com.vinmahob.presentation.productlist.viewmodel

import androidx.lifecycle.viewModelScope
import com.vinmahob.domain.productlist.model.ProductListDomainModel
import com.vinmahob.domain.productlist.usecase.GetProductListUseCase
import com.vinmahob.presentation.architecture.viewmodel.base.BaseViewModel
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.productlist.mapper.ProductListDomainToPresentationMapper
import com.vinmahob.presentation.productlist.model.ProductListSideEffect
import com.vinmahob.presentation.productlist.model.ProductListViewIntent
import com.vinmahob.presentation.productlist.model.ProductListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val productListDomainToPresentationMapper: ProductListDomainToPresentationMapper,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ProductListViewState, ProductListViewIntent, ProductListSideEffect>(
    useCaseExecutorProvider = useCaseExecutorProvider
) {
    init {
        handleViewIntent()
    }

    override fun initialState() = ProductListViewState.Idle

    private fun fetchProductList() {
        updateViewState(ProductListViewState.Loading)
        useCaseExecutor.execute(
            getProductListUseCase, Unit, ::currentProductList, ::onError
        )
    }

    private fun currentProductList(productList: ProductListDomainModel) {
        val productDetails = productListDomainToPresentationMapper.toPresentation(productList)
        updateViewState(ProductListViewState.ProductListLoaded(productList = productDetails))
    }

    private fun onError(throwable: Throwable) {
        updateViewState(ProductListViewState.Error(error = throwable.message))
    }

    private fun onProductItemClick(productId: Int) {
        emitSideEffect(ProductListSideEffect.NavigateToProductDetails(productId))
    }

    override fun handleViewIntent() {
        viewModelScope.launch {
            viewIntent.consumeAsFlow().collect {
                when (it) {
                    ProductListViewIntent.LoadProductList -> fetchProductList()
                    is ProductListViewIntent.OnProductItemClicked -> onProductItemClick(it.productId)
                }
            }
        }
    }
}