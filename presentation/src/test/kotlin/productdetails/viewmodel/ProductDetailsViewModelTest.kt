package productdetails.viewmodel

import CoroutinesTestRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vinmahob.domain.architecture.exception.UnknownDomainException
import com.vinmahob.domain.architecture.usecase.UseCaseExecutor
import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel
import com.vinmahob.domain.productdetails.usecase.GetProductDetailsUseCase
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.productdetails.mapper.ProductDetailsDomainToPresentationMapper
import com.vinmahob.presentation.productdetails.model.ProductDetailsPresentationModel
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewIntent
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewState
import com.vinmahob.presentation.productdetails.viewmodel.KEY_ID
import com.vinmahob.presentation.productdetails.viewmodel.ProductDetailsViewModel
import com.vinmahob.presentation.productlist.model.ProductListViewState
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
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

    private lateinit var getProductDetailsUseCase: GetProductDetailsUseCase
    private lateinit var productDetailsDomainToPresentationMapper: ProductDetailsDomainToPresentationMapper
    private lateinit var useCaseExecutorProvider: UseCaseExecutorProvider
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: ProductDetailsViewModel

    @Before
    fun setUp() {
        getProductDetailsUseCase = mockk()
        productDetailsDomainToPresentationMapper = ProductDetailsDomainToPresentationMapper()
        useCaseExecutorProvider = mockk()
        savedStateHandle = mockk()

        viewModel = ProductDetailsViewModel(
            getProductDetailsUseCase,
            productDetailsDomainToPresentationMapper,
            useCaseExecutorProvider,
            savedStateHandle
        )
    }

    @Test
    fun `Given viewModel is instantiated Then initial state should be idle`() {
        //act
        val initialState = viewModel.initialState()

        //assert
        assertEquals(ProductDetailsViewState.Idle, initialState)
    }

    @Test
    fun `Given fetchProductDetails View intent received Then useCaseExecutor should execute use case with given productId`() =
        runTest {
            //init
            val productId = 1
            val useCaseExecutor = mockk<UseCaseExecutor>()
            coEvery { savedStateHandle.get<Int>(KEY_ID) } returns productId
            coEvery { useCaseExecutorProvider.invoke(viewModel.viewModelScope) } returns useCaseExecutor
            every {
                useCaseExecutor.execute(
                    getProductDetailsUseCase,
                    productId,
                    viewModel::currentProductDetails,
                    viewModel::onError
                )
            } just Runs

            //act
            launch {
                viewModel.viewIntent.send(ProductDetailsViewIntent.LoadSelectedProductDetails)
            }.join()

            //assert
            verify {
                useCaseExecutor.execute(
                    getProductDetailsUseCase,
                    productId,
                    viewModel::currentProductDetails,
                    viewModel::onError
                )
            }
        }

    @Test
    fun `Given currentProductDetails Then view state should be updated with loaded product details`() {
        //init
        val productDetailsDomainModel = FakeDataProvider.fakeProductDetails1
        val productDetailsPresentationModel = FakeDataProvider.fakePresentationProductDetails1

        //act
        viewModel.currentProductDetails(productDetailsDomainModel)

        //assert
        assertTrue(viewModel.viewState.value is ProductDetailsViewState.ProductDetailsLoaded)
        val viewStateValue =
            viewModel.viewState.value as? ProductDetailsViewState.ProductDetailsLoaded
        viewStateValue?.let {
            assertEquals(it.productDetails.id, productDetailsPresentationModel.id)
        }
    }

    @Test
    fun `Given DomainException Then view state is updated to Error`() {
        //init
        val errorMsg = "Api fails to load data"
        val domainException = UnknownDomainException(errorMsg)

        //act
        viewModel.onError(domainException)

        //assert
        assertTrue(viewModel.viewState.value is ProductDetailsViewState.Error)
    }
}
