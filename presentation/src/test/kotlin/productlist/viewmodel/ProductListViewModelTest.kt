package productlist.viewmodel

import CoroutinesTestRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.architecture.model.UseCaseResult
import com.vinmahob.domain.architecture.usecase.UseCaseExecutor
import com.vinmahob.domain.productlist.repository.ProductListRepository
import com.vinmahob.domain.productlist.usecase.GetProductListUseCase
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.productlist.mapper.ProductListDomainToPresentationMapper
import com.vinmahob.presentation.productlist.mapper.ProductListItemDomainToPresentationMapper
import com.vinmahob.presentation.productlist.model.ProductListSideEffect
import com.vinmahob.presentation.productlist.model.ProductListViewIntent
import com.vinmahob.presentation.productlist.model.ProductListViewState
import com.vinmahob.presentation.productlist.viewmodel.ProductListViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import utils.FakeDataProvider

class ProductListViewModelTest {
    companion object {
        const val errorMsg = "Data fails to load"
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var useCaseExecutor: UseCaseExecutor
    private lateinit var getProductListUseCase: GetProductListUseCase
    private lateinit var productListRepository: ProductListRepository
    private lateinit var coroutineContextProvider: CoroutineContextProvider
    private lateinit var productListDomainToPresentationMapper: ProductListDomainToPresentationMapper
    private lateinit var productListItemDomainToPresentationMapper: ProductListItemDomainToPresentationMapper
    private lateinit var useCaseExecutorProvider: UseCaseExecutorProvider
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: ProductListViewModel

    @Before
    fun setUp() {
        productListRepository = mockk()
        coroutineContextProvider = mockk()
        getProductListUseCase =
            GetProductListUseCase(productListRepository, coroutineContextProvider)
        productListItemDomainToPresentationMapper = ProductListItemDomainToPresentationMapper()
        productListDomainToPresentationMapper =
            ProductListDomainToPresentationMapper(productListItemDomainToPresentationMapper)
        useCaseExecutorProvider = mockk()
        savedStateHandle = mockk()

        viewModel = ProductListViewModel(
            getProductListUseCase, productListDomainToPresentationMapper, useCaseExecutorProvider
        )
        useCaseExecutor = UseCaseExecutor(viewModel.viewModelScope)
    }

    @Test
    fun `Given view model is instantiated Then initial state should be idle`() {
        //act
        val initialState = viewModel.initialState()

        //assert
        assertEquals(ProductListViewState.Idle, initialState)
    }

    @Test
    fun `Given LoadProductList View Intent is received When product list is fetched Then view state is updated to ProductListLoaded`() {
        //init
        val productList = FakeDataProvider.fakeDomainProductList
        val expectedResult = FakeDataProvider.fakePresentationProductList

        coEvery { useCaseExecutorProvider.invoke(viewModel.viewModelScope) } returns useCaseExecutor
        coEvery { coroutineContextProvider.io } returns Dispatchers.Main
        coEvery { productListRepository.getProductList() } returns UseCaseResult.OnSuccess(
            productList
        )

        //act
        runTest {
            viewModel.viewIntent.send(ProductListViewIntent.LoadProductList)
        }

        //assert
        //assert
        assertTrue(viewModel.viewState.value is ProductListViewState.ProductListLoaded)
        val viewStateValue =
            viewModel.viewState.value as? ProductListViewState.ProductListLoaded
        viewStateValue?.let {
            assertEquals(it.productList.productList[0], expectedResult.productList[0])
        }
    }

    @Test
    fun `Given LoadProductList View Intent is received When fetching product list fails Then view state is updated to Error`() {
        //init
        val throwable = Throwable(errorMsg)

        coEvery { useCaseExecutorProvider.invoke(viewModel.viewModelScope) } returns useCaseExecutor
        coEvery { coroutineContextProvider.io } returns Dispatchers.Main
        coEvery { productListRepository.getProductList() } returns UseCaseResult.OnError(throwable)

        //act
        runTest {
            viewModel.viewIntent.send(ProductListViewIntent.LoadProductList)
        }

        //assert
        assertTrue(viewModel.viewState.value is ProductListViewState.Error)
    }

    @Test
    fun `Given list of products displayed When OnProductItemClick Then emit NavigateToProductDetails side effect`() =
        runTest {
            // init
            val productId = 1

            //collect
            val job = launch {
                viewModel.sideEffect.collect {
                    assertTrue(it is ProductListSideEffect.NavigateToProductDetails)
                }
            }

            //emit
            viewModel.viewIntent.send(ProductListViewIntent.OnProductItemClicked(productId))
            job.cancel()
        }
}
