package productlist.datasource

import com.vinmahob.datasource.productlist.datasource.ProductListLiveDataSource
import com.vinmahob.datasource.productlist.mapper.ProductListDataSourceToDataMapper
import com.vinmahob.datasource.productlist.mapper.ProductListItemDataSourceToDataMapper
import com.vinmahob.datasource.productlist.network.ProductListApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductListLiveDataSourceTest {
    private var productListApiService = mockk<ProductListApiService>()
    private lateinit var productListDataSourceToDataMapper: ProductListDataSourceToDataMapper
    private lateinit var productListItemDataSourceToDataMapper: ProductListItemDataSourceToDataMapper
    private lateinit var productListLiveDataSource: ProductListLiveDataSource

    @Before
    fun setup(){
        productListItemDataSourceToDataMapper = ProductListItemDataSourceToDataMapper()
        productListDataSourceToDataMapper = ProductListDataSourceToDataMapper(productListItemDataSourceToDataMapper)
        productListLiveDataSource = ProductListLiveDataSource(
            productListApiService = productListApiService,
            productListDataSourceToDataMapper = productListDataSourceToDataMapper
        )
    }

    @Test
    fun `Given productListApiService gives ProductListDataSourceModel When getProductList called then returns productListDataModel`() = runTest {
        //Given
        val productListDataSourceModel = FakeDataProvider.fakeProductList
        val expectedResult = FakeDataProvider.fakeDataProductList
        coEvery { productListApiService.getProductList() } returns
                productListDataSourceModel

        //when
        val actualResult = productListLiveDataSource.getProductList()

        //Then
        Assert.assertEquals(expectedResult.productList[1].brand,actualResult.productList[1].brand)
    }
}