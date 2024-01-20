package com.vinmahob.domain.productlist.usecase

import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.architecture.model.UseCaseResult
import com.vinmahob.domain.architecture.usecase.BackgroundExecutingUseCase
import com.vinmahob.domain.productlist.model.ProductListDomainModel
import com.vinmahob.domain.productlist.repository.ProductListRepository

class GetProductListUseCase(
    private val productListRepository: ProductListRepository,
    coroutineContextProvider: CoroutineContextProvider
) : BackgroundExecutingUseCase<Unit, ProductListDomainModel>(coroutineContextProvider) {
    override suspend fun executeInBackground(request: Unit): UseCaseResult<ProductListDomainModel> {
        return productListRepository.getProductList()
    }
}