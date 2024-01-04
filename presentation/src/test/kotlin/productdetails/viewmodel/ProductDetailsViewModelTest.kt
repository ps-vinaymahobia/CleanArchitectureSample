import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.vinmahob.domain.architecture.usecase.UseCaseExecutor
import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel
import com.vinmahob.domain.productdetails.usecase.GetProductDetailsUseCase
import com.vinmahob.presentation.architecture.viewmodel.usecase.UseCaseExecutorProvider
import com.vinmahob.presentation.productdetails.mapper.ProductDetailsDomainToPresentationMapper
import com.vinmahob.presentation.productdetails.model.ProductDetailsPresentationModel
import com.vinmahob.presentation.productdetails.model.ProductDetailsViewState
import com.vinmahob.presentation.productdetails.viewmodel.ProductDetailsViewModel
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProductDetailsViewModelTest {
    private lateinit var getProductDetailsUseCase: GetProductDetailsUseCase
    private lateinit var productDetailsDomainToPresentationMapper: ProductDetailsDomainToPresentationMapper
    private lateinit var useCaseExecutorProvider: UseCaseExecutorProvider
    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: ProductDetailsViewModel

    @Before
    fun setUp() {
        getProductDetailsUseCase = mockk()
        productDetailsDomainToPresentationMapper = mockk()
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
    fun `initial state should be idle`() {
        //act
        val initialState = viewModel.initialState()

        //assert
        assertEquals(ProductDetailsViewState.Idle, initialState)
    }

    @Test
    fun `fetchProductDetails should execute use case with given productId`() {
        //init
        val productId = 1
        val useCaseExecutor = mockk<UseCaseExecutor>()
        coEvery { useCaseExecutorProvider.invoke(viewModel.viewModelScope) } returns useCaseExecutor
        every { useCaseExecutor.execute(getProductDetailsUseCase, productId, viewModel::currentProductDetails) } just Runs

        //act
        viewModel.fetchProductDetails(productId)

        //assert
        verify {
            useCaseExecutor.execute(
                getProductDetailsUseCase,
                productId,
                any()
            )
        }
    }

    @Test
    fun `currentProductDetails should update view state with loaded product details`() {
        //init
        val productDetailsDomainModel = mockk<ProductDetailsDomainModel>()
        val productDetailsPresentationModel = mockk<ProductDetailsPresentationModel>()
        every { productDetailsDomainToPresentationMapper.toPresentation(productDetailsDomainModel) } returns productDetailsPresentationModel

        //act
        viewModel.currentProductDetails(productDetailsDomainModel)

        //assert
        verify {
            productDetailsDomainToPresentationMapper.toPresentation(productDetailsDomainModel)
        }
        assertTrue(viewModel.viewState.value  is ProductDetailsViewState.ProductDetailsLoaded)
    }
}
