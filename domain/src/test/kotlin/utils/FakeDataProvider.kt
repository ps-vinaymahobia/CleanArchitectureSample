package utils

import com.vinmahob.domain.productdetails.model.ProductDetailsDomainModel
import com.vinmahob.domain.productlist.model.ProductListDomainModel
import com.vinmahob.domain.productlist.model.ProductListItemDomainModel

object FakeDataProvider {
    val fakeProductDetails1 = ProductDetailsDomainModel(
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

    val fakeProductDetails2 = ProductDetailsDomainModel(
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

    val fakeProductListItem1 = ProductListItemDomainModel(
        brand = "Apple",
        id = 1,
        title = "iPhone 15",
        category = "",
        price = 200,
        stock = 1,
        rating = 2.2,
        thumbnail = ""
    )

    val fakeProductListItem2 = ProductListItemDomainModel(
        brand = "Samsung",
        id = 2,
        title = "S23",
        category = "",
        price = 400,
        stock = 1,
        rating = 2.2,
        thumbnail = ""
    )


    val fakeProductList = ProductListDomainModel(listOf(fakeProductListItem1, fakeProductListItem2))


}