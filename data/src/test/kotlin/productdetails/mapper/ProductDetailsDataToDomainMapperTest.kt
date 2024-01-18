package productdetails.mapper

import com.vinmahob.data.productdetails.mapper.ProductDetailsDataToDomainMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider

class ProductDetailsDataToDomainMapperTest {
    private lateinit var productDetailsDataToDomainMapper: ProductDetailsDataToDomainMapper

    @Before
    fun setup() {
        productDetailsDataToDomainMapper = ProductDetailsDataToDomainMapper()
    }

    @Test
    fun `Given productDetailsDataModel When toDomain fun is called Then it should return productDomainDetailsModel`() {
        //init
        val productDetailsDataModel = FakeDataProvider.fakeProductDetails1
        val expectedResult = FakeDataProvider.fakeDomainProductDetails1

        //act
        val actualResult = productDetailsDataToDomainMapper.toDomain(productDetailsDataModel)

        //assert
        Assert.assertEquals(expectedResult, actualResult)
    }
}