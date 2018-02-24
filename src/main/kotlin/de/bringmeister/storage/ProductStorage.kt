package de.bringmeister.storage

import de.bringmeister.model.Product

interface ProductStorage {

    fun all(): List<Product>

}