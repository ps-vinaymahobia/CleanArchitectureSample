package com.vinmahob.presentation.productdetails.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel
import com.vinmahob.domain.productdetails.usecase.GetProductDetailsUseCase
import com.vinmahob.presentation.architecture.viewmodel.base.BaseViewModel
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.productdetails.mapper.ProductDetailsDomainToPresentationMapper
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase,
    private val productDetailsDomainToPresentationMapper: ProductDetailsDomainToPresentationMapper,
    useCaseExecutorProvider: UseCaseExecutorProvider,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ProductDetailsViewState>(useCaseExecutorProvider = useCaseExecutorProvider) {
    override fun initialState() = ProductDetailsViewState.Idle

    init {
//        fetchProductDetails(savedStateHandle.get<Int>("id") ?: 1)
    }

    fun fetchProductDetails(productId: Int) {
        useCaseExecutor.execute(
            getProductDetailsUseCase, productId, ::currentProductDetails
        )
    }

    @VisibleForTesting
    fun currentProductDetails(product : ProductDetailsDomainModel){
        val productDetails = productDetailsDomainToPresentationMapper.toPresentation(product)
        updateViewState{ProductDetailsViewState.ProductDetailsLoaded(productDetails)}
    }
}