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
    fun `Given productDetailsDataModel when toPresentation function is called Then it return PresentationProductDetails`() {
        //init
        val productDetailsDataModel = FakeDataProvider.fakeProductDetails1
        val expectedResult = FakeDataProvider.fakePresentationProductDetails1

        //act
        val actualResult =
            productDetailsDomainToPresentationMapper.toPresentation(productDetailsDataModel)

        //assert
        Assert.assertEquals(expectedResult, actualResult)
    }
}