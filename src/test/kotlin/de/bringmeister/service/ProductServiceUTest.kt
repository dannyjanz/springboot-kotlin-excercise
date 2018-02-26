package de.bringmeister.service

import de.bringmeister.MockData
import de.bringmeister.MockProducts
import de.bringmeister.MockSkuUnitPrices
import de.bringmeister.model.Price
import de.bringmeister.model.ProductWithPrices
import de.bringmeister.storage.Query
import de.bringmeister.util.Success
import org.junit.Test

class ProductServiceUTest {

    val service = MockData.productService

    @Test
    fun `allProducts should return all Products the Storage has to offer`() {

        assert(service.allProducts() == Success(MockProducts.all))

    }

    @Test
    fun `getting a product by Id should return the requested Product with all available prices`() {

        val bananaResult = service.product(MockProducts.banana.id)

        assert(bananaResult.map { it.product } == Success(MockProducts.banana))
        assert(bananaResult.map { it.prices } == Success(listOf(MockSkuUnitPrices.a, MockSkuUnitPrices.b)))


    }

    @Test
    fun `requesting a non existing id should return an Empty result`() {

        val bananaResult = service.product("wrong id")

        assert(bananaResult == Query.emptyQueryResult<ProductWithPrices>())


    }

    @Test
    fun `getting a price for a product and unit should return exactly that price`() {
        val price = service.price(MockProducts.banana.id, MockSkuUnitPrices.a.unit)

        assert(price == Success(MockSkuUnitPrices.a.price))
    }

    @Test
    fun `getting a price for a product or unit that don't exist should return an Empty result`() {

        assert(service.price("some id", MockSkuUnitPrices.a.unit) == Query.emptyQueryResult<Price>())
        assert(service.price(MockProducts.tomato.id, MockSkuUnitPrices.a.unit) == Query.emptyQueryResult<Price>())

    }


}