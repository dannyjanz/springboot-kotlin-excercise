package de.bringmeister

import de.bringmeister.model.PackingUnit
import de.bringmeister.model.Price
import de.bringmeister.model.Product
import de.bringmeister.model.SkuUnitPrice
import de.bringmeister.service.ProductServiceInstance
import de.bringmeister.storage.FileContentProvider
import de.bringmeister.storage.ProductsFromXml
import de.bringmeister.storage.SkuUnitPricesFromJson
import java.math.BigDecimal
import java.nio.charset.Charset
import java.util.*

object MockData {

    val productService = ProductServiceInstance(ProductsFromXml(MockProducts), SkuUnitPricesFromJson(MockSkuUnitPrices))

}

object MockProducts : FileContentProvider {

    val banana = Product("1", "Banana", "Tasty Stuff", "BA-01")
    val tomato = Product("2", "Tomato", "Juicy", "TO-02")

    val all = listOf(banana, tomato)

    override fun readLines(filename: String, charset: Charset): String =
            """
                <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                <Products>
                    <Product id="1">
                        <Name>Banana</Name>
                        <Description>Tasty Stuff</Description>
                        <sku>BA-01</sku>
                    </Product>
                    <Product id="2">
                        <Name>Tomato</Name>
                        <Description>Juicy</Description>
                        <sku>TO-02</sku>
                    </Product>
                </Products>
            """.trimIndent()
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