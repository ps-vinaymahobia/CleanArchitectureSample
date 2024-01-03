package com.vinmahob.domain.productdetails.usecase

import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.architecture.usecase.BackgroundExecutingUseCase
import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel
import com.vinmahob.domain.productdetails.repository.ProductDetailsRepository

class GetProductDetailsUseCase(
    private val productDetailsRepository: ProductDetailsRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<Int,ProductDetailsDomainModel>(coroutineContextProvider){
    override suspend fun executeInBackground(request: Int): ProductDetailsDomainModel {
        return productDetailsRepository.getProductDetails(productId = request)
    }

}