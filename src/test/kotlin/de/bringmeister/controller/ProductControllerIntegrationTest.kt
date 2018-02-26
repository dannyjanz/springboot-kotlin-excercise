package de.bringmeister.controller

import de.bringmeister.Application
import de.bringmeister.model.Price
import de.bringmeister.model.Product
import de.bringmeister.util.typeOf
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [Application::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest {

    @LocalServerPort
    internal var port: Int = 0


    @Test
    fun test() {

        val requestHeaders = HttpHeaders().apply { add("accept", "application/json") }
        val restTemplate = RestTemplate()


        val allProducts = restTemplate.exchange<List<Product>>(
                "http://localhost:$port/products",
                HttpMethod.GET, HttpEntity<List<Product>>(null, requestHeaders), typeOf<List<Product>>())

        val bananaPackagePrice = restTemplate.exchange<Price>(
                "http://localhost:$port/products/${TestData.realBananaId}/prices/package",
                HttpMethod.GET, HttpEntity<Price>(null, requestHeaders), Price::class.java)

        assert(allProducts.statusCode == HttpStatus.OK)
        assert(allProducts.body[0].id == TestData.realBananaId)
        assert(allProducts.body[0].sku == TestData.realBananaSku)
        assert(allProducts.body[1].id == TestData.realTomatoId)

        assert(bananaPackagePrice.statusCode == HttpStatus.OK)
        assert(bananaPackagePrice.body.value == TestData.realBananaPackagePrice)

    }

}

object TestData {

    val realBananaId = "43b105a0-b5da-401b-a91d-32237ae418e4"
    val realBananaSku = "BA-01"
    val realBananaPackagePrice = BigDecimal("10.99")

    val realTomatoId = "b867525e-53f8-4864-8990-5f13a5dd9d14"

}
