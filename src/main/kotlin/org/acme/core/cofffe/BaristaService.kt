package org.acme.core.cofffe

import io.reactivex.Maybe

interface BaristaService {
    fun findAllBaristasForCoffeeType(coffeeType: CoffeeType): Maybe<List<Barista>>

    fun create(barista: Barista): Maybe<Barista>
}
