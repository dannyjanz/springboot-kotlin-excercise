package de.bringmeister.model

data class ProductWithPrices(val product: Product, val prices: List<SkuUnitPrice>)