package de.bringmeister.storage

import de.bringmeister.model.PackingUnit
import de.bringmeister.model.Price
import de.bringmeister.model.SkuUnitPrice
import org.junit.Test
import java.lang.Exception
import java.math.BigDecimal
import java.nio.charset.Charset
import java.util.*

class SkuUnitPricesFromJsonUTest {

    @Test
    fun `test that all entries in the JSON are being converted to SkuUnitPrices`() {

        val skuUnitPrices = SkuUnitPricesFromJson(MockSkuUnitPrices).all()

        assert(skuUnitPrices.size == MockSkuUnitPrices.all.size)
        assert(skuUnitPrices.containsAll(MockSkuUnitPrices.all))
    }

    @Test(expected = Exception::class)
    fun `test that initializing with invalid JSON leads to an exception`() {

        val test = SkuUnitPricesFromJson(object : FileContentProvider {
            override fun readLines(filename: String, charset: Charset): String =
                    "I happen not to be a properly formatted XML. In fact, I think I will produce an error"

        })

    }


}

object MockSkuUnitPrices : FileContentProvider {

    val a = SkuUnitPrice("BA-01", Price(BigDecimal("2.45"), Currency.getInstance("EUR")), PackingUnit.PIECE)
    val b = SkuUnitPrice("BA-01", Price(BigDecimal("10.99"), Currency.getInstance("EUR")), PackingUnit.PACKAGE)
    val c = SkuUnitPrice("BB-01", Price(BigDecimal("6.21"), Currency.getInstance("EUR")), PackingUnit.PIECE)

    val all = listOf(a, b, c)

    override fun readLines(filename: String, charset: Charset): String =
            """
                    [
                          {
                            "id": "BA-01",
                            "price": {
                              "value": 2.45,
                              "currency": "EUR"
                            },
                            "unit": "piece"
                          },
                          {
                            "id": "BA-01",
                            "price": {
                              "value": 10.99,
                              "currency": "EUR"
                            },
                            "unit": "package"
                          },
                          {
                            "id": "BB-01",
                            "price": {
                              "value": 6.21,
                              "currency": "EUR"
                            },
                            "unit": "piece"
                          }
                    ]
                """.trimIndent()

}