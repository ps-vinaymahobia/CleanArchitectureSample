package productlist.viewmodel

import CoroutinesTestRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vinmahob.domain.architecture.exception.UnknownDomainException
import com.vinmahob.domain.architecture.usecase.UseCaseExecutor
import com.vinmahob.domain.productlist.model.ProductListDomainModel
import com.vinmahob.domain.productlist.usecase.GetProductListUseCase
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.productlist.mapper.ProductListDomainToPresentationMapper
import com.vinmahob.presentation.productlist.mapper.ProductListItemDomainToPresentationMapper
import com.vinmahob.presentation.productlist.model.ProductListViewIntent
import com.vinmahob.presentation.productlist.model.ProductListViewState
import com.vinmahob.presentation.productlist.viewmodel.ProductListViewModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import utils.FakeDataProvider

class ProductListViewModelTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private lateinit var getProductListUseCase: GetProductListUseCase
    private lateinit var productListDomainToPresentationMapper: ProductListDomainToPresentationMapper
    private lateinit var productListItemDomainToPresentationMapper: ProductListItemDomainToPresentationMapper
    private lateinit var useCaseExecutorProvider: UseCaseExecutorProvider
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: ProductListViewModel

    @Before
    fun setUp() {
        getProductListUseCase = mockk()
        productListItemDomainToPresentationMapper = ProductListItemDomainToPresentationMapper()
        productListDomainToPresentationMapper =
            ProductListDomainToPresentationMapper(productListItemDomainToPresentationMapper)
        useCaseExecutorProvider = mockk()
        savedStateHandle = mockk()

        viewModel = ProductListViewModel(
            getProductListUseCase,
            productListDomainToPresentationMapper,
            useCaseExecutorProvider
        )
    }

    @Test
    fun `Given view model is instantiated Then initial state should be idle`() {
        //act
        val initialState = viewModel.initialState()

        //assert
        assertEquals(ProductListViewState.Idle, initialState)
    }

    @Test
    fun `Given LoadProductList View Intent is received Then fetchProductList should execute use case`() =
        runTest {
            //init
            val useCaseExecutor = mockk<UseCaseExecutor>()
            coEvery { useCaseExecutorProvider.invoke(viewModel.viewModelScope) } returns useCaseExecutor
            coEvery {
                useCaseExecutor.execute(
                    getProductListUseCase,
                    null,
                    viewModel::currentProductList,
                    viewModel::onError
                )
            } just Runs

            //act
            launch {
                viewModel.viewIntent.send(ProductListViewIntent.LoadProductList)
            }.join()

            //assert
            verify {
                useCaseExecutor.execute(
                    getProductListUseCase,
                    null,
                    viewModel::currentProductList,
                    viewModel::onError
                )
            }
        }

    @Test
    fun `Given productList Then domain to presentation mapper class is called and view state is updated to ProductListLoaded`() {
        //init
        val productListPresentationModel = FakeDataProvider.fakePresentationProductList
        val productListDomainModel = FakeDataProvider.fakeDomainProductList

        //act
        viewModel.currentProductList(productListDomainModel)

        //assert
        assertTrue(viewModel.viewState.value is ProductListViewState.ProductListLoaded)
        val viewStateValue = viewModel.viewState.value as? ProductListViewState.ProductListLoaded
        viewStateValue?.let {
            assertEquals(it.productList.productList[0], productListPresentationModel.productList[0])
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
        assertTrue(viewModel.viewState.value is ProductListViewState.Error)
    }
}
