package org.acme.core.cofffe

import io.reactivex.Maybe

interface BaristaService {

    fun findAll() : Maybe<List<Barista>>

    fun findAllBaristasForCoffeeType(coffeeType: CoffeeType): Maybe<List<Barista>>

    fun create(barista: Barista): Maybe<Barista>

    fun dropAll()
}
