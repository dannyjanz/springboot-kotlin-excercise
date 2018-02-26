package de.bringmeister.model

import com.fasterxml.jackson.annotation.JsonProperty
import de.bringmeister.util.Attempt
import de.bringmeister.util.Empty

enum class PackingUnit {

    @JsonProperty("piece")
    PIECE,

    @JsonProperty("package")
    PACKAGE;

    companion object {
        fun fromName(name: String) = Attempt {
            name.toLowerCase().let { lowerCode -> values().first { it.name.toLowerCase() == lowerCode } }
        }.recoverWith { Empty<PackingUnit>("No packing unit exists for $name") }
    }


}