package org.acme.core.cofffe

import io.reactivex.Maybe

interface PriceService {
    fun priceForCoffeeType(coffeeType: CoffeeType): Maybe<Float>
}
