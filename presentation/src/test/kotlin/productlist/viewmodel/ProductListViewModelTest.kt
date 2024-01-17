package productlist.viewmodel

import CoroutinesTestRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vinmahob.domain.architecture.usecase.UseCaseExecutor
import com.vinmahob.domain.productlist.model.ProductListDomainModel
import com.vinmahob.domain.productlist.usecase.GetProductListUseCase
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewState
import com.vinmahob.presentation.productlist.mapper.ProductListDomainToPresentationMapper
import com.vinmahob.presentation.productlist.mapper.ProductListItemDomainToPresentationMapper
import com.vinmahob.presentation.productlist.model.ProductListPresentationModel
import com.vinmahob.presentation.productlist.model.ProductListViewIntent
import com.vinmahob.presentation.productlist.model.ProductListViewState
import com.vinmahob.presentation.productlist.viewmodel.ProductListViewModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

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
        productListDomainToPresentationMapper = mockk()
        productListItemDomainToPresentationMapper = mockk()
        useCaseExecutorProvider = mockk()
        savedStateHandle = mockk()

        viewModel = ProductListViewModel(
            getProductListUseCase,
            productListDomainToPresentationMapper,
            useCaseExecutorProvider
        )
    }

    @Test
    fun `initial state should be idle`() {
        //act
        val initialState = viewModel.initialState()

        //assert
        assertEquals(ProductDetailsViewState.Idle, initialState)
    }

    @Test
    fun `fetchProductList should execute use case`() = runTest {
        //init
        val useCaseExecutor = mockk<UseCaseExecutor>()
        coEvery { useCaseExecutorProvider.invoke(viewModel.viewModelScope) } returns useCaseExecutor
        coEvery {
            useCaseExecutor.execute(
                getProductListUseCase,
                null,
                viewModel::currentProductList
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
                any()
            )
        }
    }

    @Test
    fun `currentProductList should update view state with loaded product list`() {
        //init
        val productListDomainModel = mockk<ProductListDomainModel>()
        val productListPresentationModel = mockk<ProductListPresentationModel>()
        every { productListDomainToPresentationMapper(productListDomainModel) } returns productListPresentationModel

        //act
        viewModel.currentProductList(productListDomainModel)

        //assert
        verify {
            productListDomainToPresentationMapper(productListDomainModel)
        }
        assertTrue(viewModel.viewState.value is ProductListViewState.ProductListLoaded)
    }
}
