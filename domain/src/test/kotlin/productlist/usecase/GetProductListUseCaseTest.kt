package productlist.usecase

import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
import com.vinmahob.domain.architecture.model.UseCaseResult
import com.vinmahob.domain.productlist.repository.ProductListRepository
import com.vinmahob.domain.productlist.usecase.GetProductListUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider


class GetProductListUseCaseTest {
    companion object {
        const val errorMsg = "Data fails to load"
    }

    private var productListRepository = mockk<ProductListRepository>()

    private var coroutineContextProvider = mockk<CoroutineContextProvider>()

    private lateinit var getProductListUseCase: GetProductListUseCase

    @Before
    fun setup() {
        getProductListUseCase = GetProductListUseCase(
            productListRepository = productListRepository,
            coroutineContextProvider = coroutineContextProvider
        )
    }

    @Test
    fun `When GetProductListUseCase When executeInBackground is called Then it returns OnSuccess with data`() =
        runTest {
            //Given
            val expectedList = FakeDataProvider.fakeProductList

            coEvery { productListRepository.getProductList() } returns
                    UseCaseResult.OnSuccess(expectedList)
            //when
            val actualResult = getProductListUseCase.executeInBackground(Unit)

            //Then
            Assert.assertEquals(
                expectedList.productList.size,
                (actualResult as UseCaseResult.OnSuccess).data.productList.size
            )
        }

    @Test
    fun `Given productId when executeInBackground fails Then it returns OnError`() = runTest {
        //Given
        val throwable = Throwable(errorMsg)
        coEvery { productListRepository.getProductList() } returns
                UseCaseResult.OnError(throwable)
        //when
        val actualResult = getProductListUseCase.executeInBackground(Unit)

        //Then
        Assert.assertTrue(actualResult is UseCaseResult.OnError)
    }
}