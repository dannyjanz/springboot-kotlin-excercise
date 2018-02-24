package de.bringmeister.storage

import de.bringmeister.model.Product
import org.junit.Test
import java.nio.charset.Charset

class ProductsFromXmlUTest {

    @Test
    fun `test that all entries in the XML are being correctly converted to Products`() {
        val products = ProductsFromXml(MockProducts).query.list()

        assert(products.size == MockProducts.all.size)
        assert(products.containsAll(MockProducts.all))

    }

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