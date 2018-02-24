package de.bringmeister.storage

import de.bringmeister.model.SkuUnitPrice

interface SkuUnitPriceStorage {

    fun all(): List<SkuUnitPrice>

}