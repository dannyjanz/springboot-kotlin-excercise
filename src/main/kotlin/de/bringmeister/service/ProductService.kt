package de.bringmeister.service

import de.bringmeister.model.PackingUnit
import de.bringmeister.model.Price
import de.bringmeister.model.Product
import de.bringmeister.model.ProductWithPrices
import de.bringmeister.storage.ProductStorage
import de.bringmeister.storage.SkuUnitPriceStorage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

interface ProductService {

    fun allProducts(): List<Product>
    fun product(id: String): ProductWithPrices
    fun price(id: String, unit: PackingUnit): Price
}

@Component
class ProductServiceInstance @Autowired constructor(
        private val products: ProductStorage,
        private val unitPrices: SkuUnitPriceStorage) : ProductService {

    override fun allProducts(): List<Product> = products.query.list()

    override fun product(id: String): ProductWithPrices = products.query.select { it.id == id }.map { product ->
        ProductWithPrices(product, unitPrices.query.select { it.sku == product.sku }.list())
    }.single()

    override fun price(id: String, unit: PackingUnit): Price = products.query.select { it.id == id }.map { product ->
        unitPrices.query.select { it.sku == product.sku && it.unit == unit }.map { it.price }.single()
    }.single()
}
