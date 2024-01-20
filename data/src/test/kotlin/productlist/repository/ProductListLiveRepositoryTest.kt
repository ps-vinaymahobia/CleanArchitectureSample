package productlist.repository

import com.vinmahob.data.productlist.datasource.ProductListDataSource
import com.vinmahob.data.productlist.mapper.ProductListDataToDomainMapper
import com.vinmahob.data.productlist.mapper.ProductListItemDataToDomainMapper
import com.vinmahob.data.productlist.repository.ProductListLiveRepository
import com.vinmahob.domain.architecture.model.UseCaseResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductListLiveRepositoryTest {
    private var productListDataSource = mockk<ProductListDataSource>()
    private lateinit var productListItemDataToDomainMapper: ProductListItemDataToDomainMapper
    private lateinit var productListDataToDomainMapper: ProductListDataToDomainMapper
    private lateinit var productListLiveRepository: ProductListLiveRepository

    @Before
    fun setup() {
        productListItemDataToDomainMapper = ProductListItemDataToDomainMapper()
        productListDataToDomainMapper =
            ProductListDataToDomainMapper(productListItemDataToDomainMapper)
        productListLiveRepository = ProductListLiveRepository(
            productListDataSource = productListDataSource,
            productListDataToDomainMapper = productListDataToDomainMapper
        )
    }

    @Test
    fun `Given productListDataSource gives ProductListDataModel When getProductList called then returns OnSuccess result with data`() =
        runTest {
            //Given
            val productListDataModel = FakeDataProvider.fakeProductList
            val expectedResult = FakeDataProvider.fakeDomainProductList
            coEvery { productListDataSource.getProductList() } returns
                    productListDataModel

            //when
            val actualResult = productListLiveRepository.getProductList()

            //Then
            Assert.assertEquals(
                expectedResult.productList[0].id,
                (actualResult as UseCaseResult.OnSuccess).data.productList[0].id
            )
        }

    @Test
    fun `Given productListDataSource gives throws exception When getProductList called then returns OnError`() =
        runTest {
            //Given
            val errorMsg = "Api fails to load"
            coEvery { productListDataSource.getProductList() } throws Throwable(errorMsg)

            //when
            val actualResult = productListLiveRepository.getProductList()

            //Then
            Assert.assertTrue(
                actualResult is UseCaseResult.OnError
            )
        }
}