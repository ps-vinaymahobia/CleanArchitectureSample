package productlist.usecase

import com.vinmahob.domain.architecture.coroutine.CoroutineContextProvider
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
    fun `When GetProductListUseCase When executeInBackground is called Then it returns productList`() =
        runTest {
            //Given
            val expectedList = FakeDataProvider.fakeProductList

            coEvery { productListRepository.getProductList() } returns
                    expectedList
            //when
            val actualResult = getProductListUseCase.executeInBackground(null)

            //Then
            Assert.assertEquals(expectedList.productList.size, actualResult.productList.size)
        }
}