package productdetails.datasource

import com.vinmahob.datasource.productdetails.datasource.ProductDetailsLiveDataSource
import com.vinmahob.datasource.productdetails.mapper.ProductDetailsDataSourceToDataModelMapper
import com.vinmahob.datasource.productdetails.network.ProductDetailsApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductDetailsLiveDataSourceTest {
    private var productDetailsApiService = mockk<ProductDetailsApiService>()
    private lateinit var productDetailsDataSourceToDataModelMapper: ProductDetailsDataSourceToDataModelMapper
    private lateinit var productDetailsLiveDataSource: ProductDetailsLiveDataSource

    @Before
    fun setup(){
        productDetailsDataSourceToDataModelMapper = ProductDetailsDataSourceToDataModelMapper()
        productDetailsLiveDataSource = ProductDetailsLiveDataSource(
            productDetailsApiService = productDetailsApiService,
            productDetailsDataSourceToDataModelMapper = productDetailsDataSourceToDataModelMapper
        )
    }

    @Test
    fun `Given productId and productDetailsApiService gives ProductDetailsDataSourceModel When getProductDetails called then returns productDetailsDataModel`() = runTest {
        //Given
        val productId = 1
        val productDetailsDataSourceModel = FakeDataProvider.fakeProductDetails1
        val expectedResult = FakeDataProvider.fakeDataProductDetails1
        coEvery { productDetailsApiService.getProductDetails(productId) } returns
                productDetailsDataSourceModel

        //when
        val actualResult = productDetailsLiveDataSource.getProductDetails(productId)

        //Then
        Assert.assertEquals(expectedResult.brand,actualResult.brand)
    }
}