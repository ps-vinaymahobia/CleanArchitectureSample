package com.vinmahob.presentation.productlist.viewmodel

import androidx.lifecycle.viewModelScope
import com.vinmahob.domain.productlist.model.ProductListDomainModel
import com.vinmahob.domain.productlist.usecase.GetProductListUseCase
import com.vinmahob.presentation.architecture.viewmodel.base.BaseViewModel
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.productlist.mapper.ProductListDomainToPresentationMapper
import com.vinmahob.presentation.productlist.model.ProductListViewIntent
import com.vinmahob.presentation.productlist.model.ProductListViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
    private val productListDomainToPresentationMapper: ProductListDomainToPresentationMapper,
    useCaseExecutorProvider: UseCaseExecutorProvider
) : BaseViewModel<ProductListViewState, ProductListViewIntent>(useCaseExecutorProvider = useCaseExecutorProvider) {
    init {
        handleViewIntent()
    }

    override fun initialState() = ProductListViewState.Idle

    private fun fetchProductList() {
        updateViewState { ProductListViewState.Loading }
        useCaseExecutor.execute(
            getProductListUseCase, null, ::currentProductList
        )
    }

    @VisibleForTesting
    fun currentProductList(productList: ProductListDomainModel) {
        val productDetails = productListDomainToPresentationMapper.toPresentation(productList)
        updateViewState { ProductListViewState.ProductListLoaded(productList = productDetails) }
    }

    override fun handleViewIntent() {
        viewModelScope.launch {
            viewIntent.consumeAsFlow().collect {
                when (it) {
                    ProductListViewIntent.LoadProductList -> fetchProductList()
                }
            }
        }
    }
}