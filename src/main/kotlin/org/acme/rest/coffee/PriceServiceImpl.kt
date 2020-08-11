package org.acme.rest.coffee

import io.reactivex.Maybe
import org.acme.core.cofffe.CoffeeType
import org.acme.core.cofffe.PriceService
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class PriceServiceImpl : PriceService {
    private val random: Random = Random()

    override fun priceForCoffeeType(coffeeType: CoffeeType): Maybe<Float> {
        return Maybe.just(0.0F)
    }

    override fun randomPrice() : Int {
        val res = random.nextInt(100)
        return res
    }
}