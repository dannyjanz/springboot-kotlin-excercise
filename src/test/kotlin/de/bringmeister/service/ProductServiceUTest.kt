package de.bringmeister.service

import de.bringmeister.MockData
import de.bringmeister.MockProducts
import de.bringmeister.MockSkuUnitPrices
import org.junit.Test

class ProductServiceUTest {

    val service = MockData.productService

    @Test
    fun `allProducts should return all Products the Storage has to offer`() {

        assert(service.allProducts() == MockProducts.all)

    }

    @Test
    fun `getting a product by Id should return the requested Product with all available prices`() {

        val bananaResult = service.product(MockProducts.banana.id)

        assert(bananaResult.product == MockProducts.banana)
        assert(bananaResult.prices == listOf(MockSkuUnitPrices.a, MockSkuUnitPrices.b))


    }

    @Test
    fun `getting a price for a product and unit should return exactly that price`() {
        val price = service.price(MockProducts.banana.id, MockSkuUnitPrices.a.unit)

        assert(price == MockSkuUnitPrices.a.price)
    }


}