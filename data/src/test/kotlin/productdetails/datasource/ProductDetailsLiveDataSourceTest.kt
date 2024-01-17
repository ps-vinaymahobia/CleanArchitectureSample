package productdetails.datasource

import com.vinmahob.data.productdetails.datasource.ProductDetailsLiveDataSource
import com.vinmahob.data.productdetails.network.ProductDetailsApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductDetailsLiveDataSourceTest {
    private var productDetailsApiService = mockk<ProductDetailsApiService>()
    private lateinit var productDetailsLiveDataSource: ProductDetailsLiveDataSource

    @Before
    fun setup() {
        productDetailsLiveDataSource = ProductDetailsLiveDataSource(
            productDetailsApiService = productDetailsApiService,
        )
    }

    @Test
    fun `Given productId and productDetailsApiService gives ProductDetailsDataSourceModel When getProductDetails called then returns productDetailsDataModel`() =
        runTest {
            //Given
            val productId = 1
            val productDetailsDataSourceModel = FakeDataProvider.fakeProductDetails1
            coEvery { productDetailsApiService.getProductDetails(productId) } returns
                    productDetailsDataSourceModel

            //when
            val actualResult = productDetailsLiveDataSource.getProductDetails(productId)

            //Then
            Assert.assertEquals(productDetailsDataSourceModel.brand, actualResult.brand)
        }
}