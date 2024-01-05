package productlist.mapper

import com.vinmahob.data.productlist.mapper.ProductListItemDataToDomainMapper
import com.vinmahob.datasource.productlist.mapper.ProductListItemDataSourceToDataMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductListItemDataSourceToDataMapperTest {
    private lateinit var productListItemDataSourceToDataMapper: ProductListItemDataSourceToDataMapper

    @Before
    fun setup() {
        productListItemDataSourceToDataMapper = ProductListItemDataSourceToDataMapper()
    }

    @Test
    fun `should map productListItemDataSourceModel to DataModel`() {
        //init
        val productList = FakeDataProvider.fakeProductListItem1
        val expectedResult = FakeDataProvider.fakeDataProductListItem1

        //act
        val actualResult = productListItemDataSourceToDataMapper.toData(productList)

        //assert
        Assert.assertEquals(expectedResult, actualResult)
    }
}