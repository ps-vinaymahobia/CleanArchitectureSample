package productlist.mapper

import com.vinmahob.presentation.productlist.mapper.ProductListDomainToPresentationMapper
import com.vinmahob.presentation.productlist.mapper.ProductListItemDomainToPresentationMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductListDomainToPresentationMapperTest {
    private lateinit var productListItemDomainToPresentationMapper: ProductListItemDomainToPresentationMapper
    private lateinit var productListDomainToPresentationMapper: ProductListDomainToPresentationMapper

    @Before
    fun setup() {
        productListItemDomainToPresentationMapper = ProductListItemDomainToPresentationMapper()
        productListDomainToPresentationMapper = ProductListDomainToPresentationMapper(productListItemDomainToPresentationMapper)
    }

    @Test
    fun `should map productListDataModel to DomainModel`() {
        //init
        val productList= FakeDataProvider.fakeDomainProductList
        val expectedResult = FakeDataProvider.fakePresentationProductList

        //act
        val actualResult = productListDomainToPresentationMapper.toPresentation(productList)

        //assert
        Assert.assertEquals(expectedResult, actualResult)
    }
}