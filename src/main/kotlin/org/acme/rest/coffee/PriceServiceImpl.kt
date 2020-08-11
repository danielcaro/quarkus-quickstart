package org.acme.rest.coffee

import io.reactivex.Maybe
import org.acme.core.cofffe.CoffeeType
import org.acme.core.cofffe.PriceService
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class PriceServiceImpl : PriceService {
    override fun priceForCoffeeType(coffeeType: CoffeeType): Maybe<Float> {
        return Maybe.just(0.0F)
    }
}