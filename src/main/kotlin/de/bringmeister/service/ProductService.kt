package de.bringmeister.service

import de.bringmeister.model.PackingUnit
import de.bringmeister.model.Price
import de.bringmeister.model.Product
import de.bringmeister.model.ProductWithPrices
import de.bringmeister.util.Result

interface ProductService {

    fun allProducts(): Result<List<Product>>
    fun product(id: String): Result<ProductWithPrices>
    fun price(id: String, unit: PackingUnit): Result<Price>
}

