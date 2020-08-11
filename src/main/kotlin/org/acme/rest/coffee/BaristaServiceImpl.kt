package org.acme.rest.coffee

import io.reactivex.Maybe
import org.acme.core.cofffe.Barista
import org.acme.core.cofffe.BaristaService
import org.acme.core.cofffe.CoffeeType
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class BaristaServiceImpl : BaristaService {

    private val baristasOnMemory = mutableListOf<Barista>()

    override fun findAllBaristasForCoffeeType(coffeeType: CoffeeType): Maybe<List<Barista>> {
        return baristasOnMemory
                .filter { it.specialities.contains(coffeeType) }
                .let { Maybe.just(it) }
    }

    override fun create(barista: Barista): Maybe<Barista> {
        baristasOnMemory.add(barista)
        return Maybe.just(barista)
    }

    override fun dropAll() {
        baristasOnMemory.clear()
    }

    override fun findAll(): Maybe<List<Barista>> {
        return baristasOnMemory.let { Maybe.just(it) }
    }
}