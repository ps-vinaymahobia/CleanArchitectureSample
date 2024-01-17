package productlist.mapper

import com.vinmahob.data.productlist.mapper.ProductListItemDataToDomainMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductListItemDataToDomainMapperTest {
    private lateinit var productListItemDataToDomainMapper: ProductListItemDataToDomainMapper

    @Before
    fun setup() {
        productListItemDataToDomainMapper = ProductListItemDataToDomainMapper()
    }

    @Test
    fun `should map productListItemDataModel to DomainModel`() {
        //init
        val productItem = FakeDataProvider.fakeProductListItem1
        val expectedResult = FakeDataProvider.fakeDomainProductListItem1

        //act
        val actualResult = productListItemDataToDomainMapper(productItem)

        //assert
        Assert.assertEquals(expectedResult, actualResult)
    }
}