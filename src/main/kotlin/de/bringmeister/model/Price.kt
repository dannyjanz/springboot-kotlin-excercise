package de.bringmeister.model

import java.math.BigDecimal

data class Currency(val name: String, val code : String )

data class Price(val value: BigDecimal, val currency : Currency )


data class Greeting(val id: Long, val content: String)