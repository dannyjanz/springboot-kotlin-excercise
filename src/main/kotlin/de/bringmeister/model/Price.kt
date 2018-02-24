package de.bringmeister.model

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.util.Currency

data class Price(

        @JsonProperty("value")
        val value: BigDecimal,

        @JsonProperty("currency")
        val currency: Currency
)



