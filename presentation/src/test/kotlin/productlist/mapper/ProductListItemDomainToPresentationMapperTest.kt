package productlist.mapper

import com.vinmahob.presentation.productlist.mapper.ProductListItemDomainToPresentationMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductListItemDomainToPresentationMapperTest {
    private lateinit var productListItemDomainToPresentationMapper: ProductListItemDomainToPresentationMapper

    @Before
    fun setup() {
        productListItemDomainToPresentationMapper = ProductListItemDomainToPresentationMapper()
    }

    @Test
    fun `should map productListItemDataModel to DomainModel`() {
        //init
        val productItem = FakeDataProvider.fakeProductListItem1
        val expectedResult = FakeDataProvider.fakePresentationProductListItem1

        //act
        val actualResult = productListItemDomainToPresentationMapper(productItem)

        //assert
        Assert.assertEquals(expectedResult, actualResult)
    }
}