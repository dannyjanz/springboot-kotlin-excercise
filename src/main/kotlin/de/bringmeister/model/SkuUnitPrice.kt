package de.bringmeister.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SkuUnitPrice(
        @JsonProperty("id") val sku: String,
        @JsonProperty("price") val price: Price,
        @JsonProperty("unit") val unit: PackingUnit
)