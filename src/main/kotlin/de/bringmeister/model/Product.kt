package de.bringmeister.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Product(
        @JsonProperty("id") val id: String,
        @JsonProperty("Name") val name: String,
        @JsonProperty("Description") val description: String,
        @JsonProperty("sku") val sku: String
)