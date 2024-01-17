package productdetails.mapper

import com.vinmahob.presentation.productdetails.mapper.ProductDetailsDomainToPresentationMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductDetailsDomainToPresentationMapperTest {
    private lateinit var productDetailsDomainToPresentationMapper: ProductDetailsDomainToPresentationMapper

    @Before
    fun setup() {
        productDetailsDomainToPresentationMapper = ProductDetailsDomainToPresentationMapper()
    }

    @Test
    fun `should map productDetailsDataModel to DomainModel`() {
        //init
        val productDetailsDataModel = FakeDataProvider.fakeProductDetails1
        val expectedResult = FakeDataProvider.fakePresentationProductDetails1

        //act
        val actualResult = productDetailsDomainToPresentationMapper.invoke(productDetailsDataModel)

        //assert
        Assert.assertEquals(expectedResult,actualResult)
    }
}