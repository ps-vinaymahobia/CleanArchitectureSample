package productdetails.repository

import com.vinmahob.data.productdetails.datasource.ProductDetailsDataSource
import com.vinmahob.data.productdetails.mapper.ProductDetailsDataToDomainMapper
import com.vinmahob.data.productdetails.repository.ProductDetailsLiveRepository
import com.vinmahob.domain.architecture.model.UseCaseResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductDetailsLiveRepositoryTest {
    companion object{
        const val errorMsg = "Api fails to load"
    }
    private var productDetailsDataSource = mockk<ProductDetailsDataSource>()
    private lateinit var productDetailsDataToDomainMapper: ProductDetailsDataToDomainMapper
    private lateinit var productDetailsLiveRepository: ProductDetailsLiveRepository

    @Before
    fun setup() {
        productDetailsDataToDomainMapper = ProductDetailsDataToDomainMapper()
        productDetailsLiveRepository = ProductDetailsLiveRepository(
            productDetailsDataSource = productDetailsDataSource,
            productDetailsDataToDomainMapper = productDetailsDataToDomainMapper
        )
    }

    @Test
    fun `Given productId and productDetailsDataSource gives ProductDetailsDataModel When getProductDetails called Then returns OnSuccess with data`() =
        runTest {
            //Given
            val productId = 1
            val productDetailsDataModel = FakeDataProvider.fakeProductDetails1
            val expectedResult = FakeDataProvider.fakeDomainProductDetails1
            coEvery { productDetailsDataSource.getProductDetails(productId) } returns
                    productDetailsDataModel

            //when
            val actualResult = productDetailsLiveRepository.getProductDetails(productId)

            //Then
            Assert.assertTrue(actualResult is UseCaseResult.OnSuccess)
            Assert.assertEquals(
                expectedResult.brand,
                (actualResult as UseCaseResult.OnSuccess).data.brand
            )
        }

    @Test
    fun `Given productDetailsDataSource gives throws exception When getProductList called then returns OnError`() =
        runTest {
            //Given
            val productId = 1
            coEvery { productDetailsDataSource.getProductDetails(productId) } throws Throwable(
                errorMsg
            )

            //when
            val actualResult = productDetailsLiveRepository.getProductDetails(productId)

            //Then
            Assert.assertTrue(
                actualResult is UseCaseResult.OnError
            )
        }
}