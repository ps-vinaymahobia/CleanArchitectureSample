package productdetails.viewmodel

import CoroutinesTestRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.architecture.model.UseCaseResult
import com.vinmahob.domain.architecture.usecase.UseCaseExecutor
import com.vinmahob.domain.productdetails.repository.ProductDetailsRepository
import com.vinmahob.domain.productdetails.usecase.GetProductDetailsUseCase
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.productdetails.mapper.ProductDetailsDomainToPresentationMapper
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewIntent
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewState
import com.vinmahob.presentation.productdetails.viewmodel.KEY_ID
import com.vinmahob.presentation.productdetails.viewmodel.ProductDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import utils.FakeDataProvider

class ProductDetailsViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var coroutineContextProvider: CoroutineContextProvider
    private lateinit var useCaseExecutor: UseCaseExecutor
    private lateinit var productDetailsRepository: ProductDetailsRepository
    private lateinit var getProductDetailsUseCase: GetProductDetailsUseCase
    private lateinit var productDetailsDomainToPresentationMapper: ProductDetailsDomainToPresentationMapper
    private lateinit var useCaseExecutorProvider: UseCaseExecutorProvider
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: ProductDetailsViewModel

    @Before
    fun setUp() {
        productDetailsRepository = mockk()
        coroutineContextProvider = mockk()
        getProductDetailsUseCase =
            GetProductDetailsUseCase(productDetailsRepository, coroutineContextProvider)
        productDetailsDomainToPresentationMapper = ProductDetailsDomainToPresentationMapper()
        useCaseExecutorProvider = mockk()
        savedStateHandle = mockk()

        viewModel = ProductDetailsViewModel(
            getProductDetailsUseCase,
            productDetailsDomainToPresentationMapper,
            useCaseExecutorProvider,
            savedStateHandle
        )
        useCaseExecutor = UseCaseExecutor(viewModel.viewModelScope)
    }

    @Test
    fun `Given viewModel is instantiated Then initial state should be idle`() {
        //act
        val initialState = viewModel.initialState()

        //assert
        assertEquals(ProductDetailsViewState.Idle, initialState)
    }

    @Test
    fun `Given fetchProductDetails View Intent is received When product details is fetched Then view state is updated to ProductDetailsLoaded`() {
        //init
        val productId = 1
        val productDetailsDomainModel = FakeDataProvider.fakeProductDetails1
        val expectedResult = FakeDataProvider.fakePresentationProductDetails1

        coEvery { savedStateHandle.get<Int>(KEY_ID) } returns productId
        coEvery { useCaseExecutorProvider.invoke(viewModel.viewModelScope) } returns useCaseExecutor
        coEvery { coroutineContextProvider.io } returns Dispatchers.Main
        coEvery { productDetailsRepository.getProductDetails(productId) } returns UseCaseResult.OnSuccess(
            productDetailsDomainModel
        )

        //act
        runTest {
            viewModel.viewIntent.send(ProductDetailsViewIntent.LoadSelectedProductDetails)
        }

        //assert
        assertTrue(viewModel.viewState.value is ProductDetailsViewState.ProductDetailsLoaded)
        val viewStateValue =
            viewModel.viewState.value as? ProductDetailsViewState.ProductDetailsLoaded
        viewStateValue?.let {
            assertEquals(it.productDetails.id, expectedResult.id)
        }
    }

    @Test
    fun `Given LoadSelectedProductDetails View Intent is received When fetching product details fails Then view state is updated to Error`() {
        //init
        val productId = 1
        val errorMsg = "Api fails to load data"
        val throwable = Throwable(errorMsg)

        coEvery { savedStateHandle.get<Int>(KEY_ID) } returns productId
        coEvery { useCaseExecutorProvider.invoke(viewModel.viewModelScope) } returns useCaseExecutor
        coEvery { coroutineContextProvider.io } returns Dispatchers.Main
        coEvery { productDetailsRepository.getProductDetails(productId) } returns UseCaseResult.OnError(throwable)

        //act
        runTest {
            viewModel.viewIntent.send(ProductDetailsViewIntent.LoadSelectedProductDetails)
        }

        //assert
        assertTrue(viewModel.viewState.value is ProductDetailsViewState.Error)
    }
}
