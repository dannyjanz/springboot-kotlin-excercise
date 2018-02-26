package de.bringmeister.controller

import de.bringmeister.model.PackingUnit
import de.bringmeister.service.ProductService
import de.bringmeister.util.Empty
import de.bringmeister.util.Failure
import de.bringmeister.util.Result
import de.bringmeister.util.Success
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController @Autowired constructor(val productService: ProductService) {

    @GetMapping("/products", produces = ["application/json", "application/xml"])
    fun products() = productService.allProducts().asResponse()

    @GetMapping("/products/{id}", produces = ["application/json", "application/xml"])
    fun productWithPrices(@PathVariable id: String) = productService.product(id).asResponse()

    @GetMapping("/products/{id}/prices/{unit}", produces = ["application/json", "application/xml"])
    fun price(@PathVariable id: String, @PathVariable unit: String) =
            PackingUnit.fromName(unit).flatMap { productService.price(id, it) }.asResponse()

}


fun <T> Result<T>.asResponse() = this.let { result ->
    when (result) {
        is Success -> ResponseEntity(result.value, HttpStatus.OK)
        is Empty -> ResponseEntity(result.message, HttpStatus.NOT_FOUND)
        is Failure -> ResponseEntity(result.error.message, HttpStatus.INTERNAL_SERVER_ERROR)
        else -> ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }
}