package de.bringmeister.service

import de.bringmeister.storage.MockProducts
import de.bringmeister.storage.MockSkuUnitPrices
import de.bringmeister.storage.ProductsFromXml
import de.bringmeister.storage.SkuUnitPricesFromJson
import org.junit.Test

class ProductServiceUTest {

    val service = ProductService(ProductsFromXml(MockProducts), SkuUnitPricesFromJson(MockSkuUnitPrices))

    @Test
    fun `allProducts should return all Products the respective Storage has to offer`() {

        assert(service.allProducts() == MockProducts.all)

    }

    @Test
    fun `getting a product by Id should return the requested Product with all available prices`() {

        val bananaResult = service.product(MockProducts.banana.id)

        assert(bananaResult.product == MockProducts.banana)
        assert(bananaResult.prices == listOf(MockSkuUnitPrices.a, MockSkuUnitPrices.b))


    }

    @Test
    fun `getting a price`() {
        val price = service.price(MockProducts.banana.id, MockSkuUnitPrices.a.unit)

        assert(price == MockSkuUnitPrices.a.price)
    }


}