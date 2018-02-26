package de.bringmeister.storage

import de.bringmeister.MockProducts
import de.bringmeister.util.Success
import org.junit.Test

class ProductsFromXmlUTest {

    @Test
    fun `test that all entries in the XML are being correctly converted to Products`() {
        val products = ProductsFromXml(MockProducts).query.list()

        assert(products.map { it.size } == Success(MockProducts.all.size))
        assert(products == Success(MockProducts.all))

    }

}


