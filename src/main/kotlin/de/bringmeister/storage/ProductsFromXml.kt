package de.bringmeister.storage

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import de.bringmeister.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ProductsFromXml @Autowired constructor(fileProvider : FileContentProvider) : ProductStorage {

    private val mapper = XmlMapper().registerKotlinModule()
    private val xml = fileProvider.readLines("products/products.xml")

    private val products: List<Product> = mapper.readValue(xml,
            mapper.typeFactory.constructCollectionLikeType(List::class.java, Product::class.java))

    override fun all(): List<Product> =
            products


}