package utils

import com.vinmahob.data.productdetails.model.ProductDetailsDataModel
import com.vinmahob.data.productlist.model.ProductListDataModel
import com.vinmahob.data.productlist.model.ProductListItemDataModel
import com.vinmahob.datasource.productdetails.model.ProductDetailsDataSourceModel
import com.vinmahob.datasource.productlist.model.ProductListDataSourceModel
import com.vinmahob.datasource.productlist.model.ProductListItemDataSourceModel

object FakeDataProvider {
    val fakeProductDetails1 = ProductDetailsDataSourceModel(
        brand = "Samsung",
        id = 1,
        title = "S23",
        category = "",
        description = "",
        price = 400,
        stock = 2,
        discountPercentage = 2.2,
        images = emptyList(),
        rating = 2.2,
        thumbnail = ""

    )

    val fakeProductDetails2 = ProductDetailsDataSourceModel(
        brand = "Apple",
        id = 2,
        title = "iPhone 15",
        category = "",
        description = "",
        price = 200,
        stock = 1,
        discountPercentage = 2.2,
        images = emptyList(),
        rating = 2.2,
        thumbnail = ""

    )

    val fakeProductListItem1 = ProductListItemDataSourceModel(
        brand = "Apple",
        id = 1,
        title = "iPhone 15",
        category = "",
        price = 200,
        stock = 1,
        rating = 2.2,
        thumbnail = "",
        images = emptyList(),
        description = "",
        discountPercentage = 2.2
    )

    val fakeProductListItem2 = ProductListItemDataSourceModel(
        brand = "Samsung",
        id = 2,
        title = "S23",
        category = "",
        price = 400,
        stock = 1,
        rating = 2.2,
        thumbnail = "",
        images = emptyList(),
        description = "",
        discountPercentage = 2.2
    )


    val fakeProductList = ProductListDataSourceModel(listOf(fakeProductListItem1, fakeProductListItem2))


    val fakeDataProductListItem1 = ProductListItemDataModel(
        brand = "Apple",
        id = 1,
        title = "iPhone 15",
        category = "",
        price = 200,
        stock = 1,
        rating = 2.2,
        thumbnail = "",
        images = emptyList(),
        description = "",
        discountPercentage = 2.2
    )

    val fakeDataProductListItem2 = ProductListItemDataModel(
        brand = "Samsung",
        id = 2,
        title = "S23",
        category = "",
        price = 400,
        stock = 1,
        rating = 2.2,
        thumbnail = "",
        images = emptyList(),
        description = "",
        discountPercentage = 2.2
    )


    val fakeDataProductList = ProductListDataModel(listOf(fakeDataProductListItem1, fakeDataProductListItem2))

    val fakeDataProductDetails1 = ProductDetailsDataModel(
        brand = "Samsung",
        id = 1,
        title = "S23",
        category = "",
        description = "",
        price = 400,
        stock = 2,
        discountPercentage = 2.2,
        images = emptyList(),
        rating = 2.2,
        thumbnail = ""
    )

}