package de.bringmeister.storage

import de.bringmeister.MockProducts
import org.junit.Test

class ProductsFromXmlUTest {

    @Test
    fun `test that all entries in the XML are being correctly converted to Products`() {
        val products = ProductsFromXml(MockProducts).query.list()

        assert(products.size == MockProducts.all.size)
        assert(products.containsAll(MockProducts.all))

    }

}


