package de.bringmeister.service

import de.bringmeister.model.PackingUnit
import de.bringmeister.model.Price
import de.bringmeister.model.Product
import de.bringmeister.model.ProductWithPrices
import de.bringmeister.storage.ProductStorage
import de.bringmeister.storage.SkuUnitPriceStorage
import de.bringmeister.util.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProductServiceInstance @Autowired constructor(
        private val products: ProductStorage,
        private val unitPrices: SkuUnitPriceStorage) : ProductService {

    override fun allProducts(): Result<List<Product>> = products.query.list()

    override fun product(id: String): Result<ProductWithPrices> = products.query.select { it.id == id }.join { product ->
        unitPrices.query.select { it.sku == product.sku }.list().map { ProductWithPrices(product, it) }
    }.single()

    override fun price(id: String, unit: PackingUnit): Result<Price> = products.query.select { it.id == id }.join { product ->
        unitPrices.query.select { it.sku == product.sku && it.unit == unit }.map { it.price }.single()
    }.single()
}
