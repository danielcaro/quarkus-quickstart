package org.acme.core.cofffe

open class MakeCoffeeOrderRequest(
        val requestedBy: String,
        val type: CoffeeType
)