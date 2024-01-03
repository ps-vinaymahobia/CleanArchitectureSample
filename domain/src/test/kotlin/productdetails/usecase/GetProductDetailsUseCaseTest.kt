package productdetails.usecase

import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel
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
    private var productDetailsRepository =  mockk<ProductDetailsRepository>()

    private var coroutineContextProvider =  mockk<CoroutineContextProvider>()

    private lateinit var getProductDetailsUseCase : GetProductDetailsUseCase

    @Before
    fun setup(){
        getProductDetailsUseCase = GetProductDetailsUseCase(
            productDetailsRepository = productDetailsRepository,
            coroutineContextProvider = coroutineContextProvider
        )
    }

    @Test
    fun `Given productId when executeInBackground then returns productDetails`() = runTest{
        //Given
        val productId = 2
        val expectedProduct = FakeDataProvider.fakeProductDetails2
        coEvery { productDetailsRepository.getProductDetails(productId) } returns
                expectedProduct
        //when
        val actualResult = getProductDetailsUseCase.executeInBackground(productId)

        //Then
        Assert.assertEquals(expectedProduct.id,actualResult.id)
    }
}