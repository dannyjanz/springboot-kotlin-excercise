package de.bringmeister.controller

import com.jayway.jsonpath.JsonPath
import de.bringmeister.MockData
import org.junit.Before
import org.junit.Test
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders


class ProductControllerUTest {

    lateinit var mvc: MockMvc

    @Before
    fun setup() {
        mvc = MockMvcBuilders.standaloneSetup(ProductController(MockData.productService)).build()
    }

    @Test
    fun `the route for all products returns all products in JSON format`() {

        val responseBody = mvc.perform(get("/products/")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful)
                .andReturn().response.contentAsString

        assert(JsonPath.read<String>(responseBody, "$[0]['id']") == "1")
        assert(JsonPath.read<String>(responseBody, "$[0]['Name']") == "Banana")

        assert(JsonPath.read<String>(responseBody, "$[1]['id']") == "2")
        assert(JsonPath.read<String>(responseBody, "$[1]['Name']") == "Tomato")

    }

    @Test
    fun `the route for a single product returns the product with all its prices in JSON format`() {

        val responseBody = mvc.perform(get("/products/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful)
                .andReturn().response.contentAsString

        val test: Any = JsonPath.read(responseBody, "$.prices[1].price.value")
        println(test)

        assert(JsonPath.read<String>(responseBody, "$.product.Name") == "Banana")
        assert(JsonPath.read<String>(responseBody, "$.product.id") == "1")

        assert(JsonPath.read<String>(responseBody, "$.prices[0].id") == "BA-01")
        assert(JsonPath.read<String>(responseBody, "$.prices[1].id") == "BA-01")

    }

    @Test
    fun `the route for a single product returns a 400 code when a non existing id is used`() {
        mvc.perform(get("/products/3")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError)
    }

    @Test
    fun `the route for a single price for a product and unit returns the requested price in JSON format`() {

        val responseBody = mvc.perform(get("/products/1/prices/piece")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful)
                .andReturn().response.contentAsString

        assert(responseBody == """{"value":2.45,"currency":"EUR"}""")

    }

    @Test
    fun `the route for a single price returns a 400 code when either the product or the price doesn't exist`() {

        mvc.perform(get("/products/3/prices/piece")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError)
                .andReturn().response.contentAsString

        mvc.perform(get("/products/2/prices/package")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError)
                .andReturn().response.contentAsString


    }

    @Test
    fun `using a non existing unit name does not lead to a server error but to a client error`() {

        mvc.perform(get("/products/1/prices/barrel")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError)
                .andReturn().response.contentAsString
    }


}

