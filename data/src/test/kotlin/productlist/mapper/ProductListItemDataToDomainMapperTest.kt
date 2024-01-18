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
    fun `Given productItemDataModel When toDomain fun is called Then it should return productListItemDomainModel`() {
        //init
        val productItem = FakeDataProvider.fakeProductListItem1
        val expectedResult = FakeDataProvider.fakeDomainProductListItem1

        //act
        val actualResult = productListItemDataToDomainMapper.toDomain(productItem)

        //assert
        Assert.assertEquals(expectedResult, actualResult)
    }
}