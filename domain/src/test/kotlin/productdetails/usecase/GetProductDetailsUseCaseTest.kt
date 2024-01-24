package productdetails.usecase

import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.architecture.model.UseCaseResult
import com.vinmahob.domain.productdetails.repository.ProductDetailsRepository
import com.vinmahob.domain.productdetails.usecase.GetProductDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider


class GetProductDetailsUseCaseTest {
    companion object {
        const val errorMsg = "Data fails to load"
    }

    private var productDetailsRepository = mockk<ProductDetailsRepository>()
    private var coroutineContextProvider = mockk<CoroutineContextProvider>()

    private lateinit var getProductDetailsUseCase: GetProductDetailsUseCase

    @Before
    fun setup() {
        getProductDetailsUseCase = GetProductDetailsUseCase(
            productDetailsRepository = productDetailsRepository,
            coroutineContextProvider = coroutineContextProvider
        )
    }

    @Test
    fun `Given productId when executeInBackground Then it returns OnSuccess with data`() = runTest {
        //Given
        val productId = 2
        val expectedProduct = FakeDataProvider.fakeProductDetails2
        coEvery { productDetailsRepository.getProductDetails(productId) } returns
                UseCaseResult.OnSuccess(expectedProduct)
        //when
        val actualResult = getProductDetailsUseCase.executeInBackground(productId)

        //Then
        Assert.assertEquals(expectedProduct.id, (actualResult as UseCaseResult.OnSuccess).data.id)
    }

    @Test
    fun `Given productId when executeInBackground fails Then it returns OnError`() = runTest {
        //Given
        val productId = 2
        val throwable = Throwable(errorMsg)
        coEvery { productDetailsRepository.getProductDetails(productId) } returns
                UseCaseResult.OnError(throwable)
        //when
        val actualResult = getProductDetailsUseCase.executeInBackground(productId)

        //Then
        Assert.assertTrue(actualResult is UseCaseResult.OnError)
    }
}