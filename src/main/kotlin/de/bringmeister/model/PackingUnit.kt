package de.bringmeister.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class PackingUnit {

    @JsonProperty("piece")
    PIECE,

    @JsonProperty("package")
    PACKAGE;

    companion object {
        fun fromCode(code: String) = code.toLowerCase().let { lowerCode ->
            values().filter { it.name.toLowerCase() == lowerCode }.first()
        }
    }


}