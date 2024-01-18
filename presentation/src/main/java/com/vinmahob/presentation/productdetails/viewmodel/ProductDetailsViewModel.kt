package com.vinmahob.presentation.productdetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vinmahob.domain.architecture.exception.DomainException
import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel
import com.vinmahob.domain.productdetails.usecase.GetProductDetailsUseCase
import com.vinmahob.presentation.architecture.viewmodel.base.BaseViewModel
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.productdetails.mapper.ProductDetailsDomainToPresentationMapper
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewIntent
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

const val KEY_ID = "id"

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val productDetailsDomainToPresentationMapper: ProductDetailsDomainToPresentationMapper,
    useCaseExecutorProvider: UseCaseExecutorProvider,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<ProductDetailsViewState, ProductDetailsViewIntent>(useCaseExecutorProvider = useCaseExecutorProvider) {
    init {
        handleViewIntent()
    }

    override fun initialState() = ProductDetailsViewState.Idle

    private fun fetchProductDetails(productId: Int) {
        updateViewState { ProductDetailsViewState.Loading }
        useCaseExecutor.execute(
            getProductDetailsUseCase, productId, ::currentProductDetails, ::onError
        )
    }

    private fun currentProductDetails(product: ProductDetailsDomainModel) {
        val productDetails = productDetailsDomainToPresentationMapper.toPresentation(product)
        updateViewState { ProductDetailsViewState.ProductDetailsLoaded(productDetails) }
    }

    private fun onError(domainException: DomainException) {
        updateViewState { ProductDetailsViewState.Error(error = domainException.message) }
    }

    override fun handleViewIntent() {
        viewModelScope.launch {
            viewIntent.consumeAsFlow().collect {
                when (it) {
                    ProductDetailsViewIntent.LoadSelectedProductDetails -> {
                        fetchProductDetails(savedStateHandle.get<Int>(KEY_ID) ?: 1)
                    }
                }
            }
        }
    }
}