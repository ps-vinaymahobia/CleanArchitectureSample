package productlist.mapper

import com.vinmahob.datasource.productlist.mapper.ProductListDataSourceToDataMapper
import com.vinmahob.datasource.productlist.mapper.ProductListItemDataSourceToDataMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductListDataSourceToDataMapperTest {
    private lateinit var productListItemDataSourceToDataMapper: ProductListItemDataSourceToDataMapper
    private lateinit var productListDataSourceToDataMapper: ProductListDataSourceToDataMapper

    @Before
    fun setup() {
        productListItemDataSourceToDataMapper = ProductListItemDataSourceToDataMapper()
        productListDataSourceToDataMapper =
            ProductListDataSourceToDataMapper(productListItemDataSourceToDataMapper)
    }

    @Test
    fun `should map productListDataModel to DomainModel`() {
        //init
        val productList = FakeDataProvider.fakeProductList
        val expectedResult = FakeDataProvider.fakeDataProductList

        //act
        val actualResult = productListDataSourceToDataMapper(productList)

        //assert
        Assert.assertEquals(expectedResult, actualResult)
    }
}