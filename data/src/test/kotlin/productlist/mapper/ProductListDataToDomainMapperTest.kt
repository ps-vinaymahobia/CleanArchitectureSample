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
    fun `Given productListDataModel When toDomain fun is called Then it should return productListDomainModel`() {
        //init
        val productList = FakeDataProvider.fakeProductList
        val expectedResult = FakeDataProvider.fakeDomainProductList

        //act
        val actualResult = productListDataToDomainMapper.toDomain(productList)

        //assert
        Assert.assertEquals(expectedResult, actualResult)
    }
}