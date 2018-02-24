package de.bringmeister.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class PackingUnit {

    @JsonProperty("piece")
    PIECE,

    @JsonProperty("package")
    PACKAGE
}