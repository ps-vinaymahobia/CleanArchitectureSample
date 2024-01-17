package productlist.mapper

import com.vinmahob.data.productlist.mapper.ProductListDataToDomainMapper
import com.vinmahob.data.productlist.mapper.ProductListItemDataToDomainMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductListDataToDomainMapperTest {
    private lateinit var productListItemDataToDomainMapper: ProductListItemDataToDomainMapper
    private lateinit var productListDataToDomainMapper: ProductListDataToDomainMapper

    @Before
    fun setup() {
        productListItemDataToDomainMapper = ProductListItemDataToDomainMapper()
        productListDataToDomainMapper =
            ProductListDataToDomainMapper(productListItemDataToDomainMapper)
    }

    @Test
    fun `should map productListDataModel to DomainModel`() {
        //init
        val productList = FakeDataProvider.fakeProductList
        val expectedResult = FakeDataProvider.fakeDomainProductList

        //act
        val actualResult = productListDataToDomainMapper(productList)

        //assert
        Assert.assertEquals(expectedResult, actualResult)
    }
}