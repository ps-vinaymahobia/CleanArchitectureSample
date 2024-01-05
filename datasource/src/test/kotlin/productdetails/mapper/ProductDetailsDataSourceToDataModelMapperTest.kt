package productdetails.mapper

import com.vinmahob.datasource.productdetails.mapper.ProductDetailsDataSourceToDataModelMapper
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import utils.FakeDataProvider


class ProductDetailsDataSourceToDataModelMapperTest {
    private lateinit var productDetailsDataSourceToDataMapper: ProductDetailsDataSourceToDataModelMapper

    @Before
    fun setup() {
        productDetailsDataSourceToDataMapper = ProductDetailsDataSourceToDataModelMapper()
    }

    @Test
    fun `should map productDetailsDataSourceModel to DataModel`() {
        //init
        val productDetailsDataModel = FakeDataProvider.fakeProductDetails1
        val expectedResult = FakeDataProvider.fakeDataProductDetails1

        //act
        val actualResult = productDetailsDataSourceToDataMapper.toData(productDetailsDataModel)

        //assert
        Assert.assertEquals(expectedResult,actualResult)
    }
}