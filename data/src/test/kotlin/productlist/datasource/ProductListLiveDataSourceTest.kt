package productlist.datasource


import com.vinmahob.data.productlist.datasource.ProductListLiveDataSource
import com.vinmahob.data.productlist.network.ProductListApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductListLiveDataSourceTest {
    private var productListApiService = mockk<ProductListApiService>()
    private lateinit var productListLiveDataSource: ProductListLiveDataSource

    @Before
    fun setup() {
        productListLiveDataSource = ProductListLiveDataSource(
            productListApiService = productListApiService,
        )
    }

    @Test
    fun `Given productListApiService gives ProductListDataSourceModel When getProductList called then returns productListDataModel`() =
        runTest {
            //Given
            val productListDataSourceModel = FakeDataProvider.fakeProductList
            coEvery { productListApiService.getProductList() } returns
                    productListDataSourceModel

            //when
            val actualResult = productListLiveDataSource.getProductList()

            //Then
            Assert.assertEquals(
                productListDataSourceModel.products[1].brand,
                actualResult.products[1].brand
            )
        }
}