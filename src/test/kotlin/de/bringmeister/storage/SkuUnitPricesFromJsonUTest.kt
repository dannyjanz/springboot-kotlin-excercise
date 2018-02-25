package de.bringmeister.storage

import de.bringmeister.MockSkuUnitPrices
import org.junit.Test
import java.lang.Exception
import java.nio.charset.Charset

class SkuUnitPricesFromJsonUTest {

    @Test
    fun `test that all entries in the JSON are being converted to SkuUnitPrices`() {

        val skuUnitPrices = SkuUnitPricesFromJson(MockSkuUnitPrices).query.list()

        assert(skuUnitPrices.size == MockSkuUnitPrices.all.size)
        assert(skuUnitPrices.containsAll(MockSkuUnitPrices.all))
    }

    @Test(expected = Exception::class)
    fun `test that initializing with invalid JSON leads to an exception`() {

        SkuUnitPricesFromJson(object : FileContentProvider {
            override fun readLines(filename: String, charset: Charset): String =
                    "I happen not to be a properly formatted XML. In fact, I think I will produce an error"

        })

    }

}

