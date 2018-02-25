package de.bringmeister.controller

import de.bringmeister.model.PackingUnit
import de.bringmeister.model.Price
import de.bringmeister.model.ProductWithPrices
import de.bringmeister.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController @Autowired constructor(val productService: ProductService) {

    @GetMapping("/products", produces = ["application/json", "application/xml"])
    fun products() = productService.allProducts()

    @GetMapping("/products/{id}", produces = ["application/json", "application/xml"])
    fun productWithPrices(@PathVariable id: String): ProductWithPrices = productService.product(id)

    @GetMapping("/products/{id}/prices/{unit}", produces = ["application/json", "application/xml"])
    fun price(@PathVariable id: String, @PathVariable unit: String): Price = productService.price(id, PackingUnit.fromCode(unit))

}