package de.bringmeister.storage

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import de.bringmeister.model.SkuUnitPrice
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SkuUnitPricesFromJson @Autowired constructor(fileProvider: FileContentProvider) : SkuUnitPriceStorage {

    private val mapper = ObjectMapper().registerKotlinModule()
    private val json = fileProvider.readLines("products/prices.json")
    private val prices: List<SkuUnitPrice> = mapper.readValue(json,
            mapper.typeFactory.constructCollectionLikeType(List::class.java, SkuUnitPrice::class.java))

    override val query: Query<SkuUnitPrice> = SimpleSequenceQuery(prices.asSequence())


}
