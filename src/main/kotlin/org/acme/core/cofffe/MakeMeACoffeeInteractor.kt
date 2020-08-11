package org.acme.core.cofffe

import io.reactivex.Maybe
import io.reactivex.functions.BiFunction
import java.util.*
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
open class MakeMeACoffeeInteractor(
        @Inject private val baristaService: BaristaService,
        @Inject private val priceService: PriceService
) {

    fun execute(request: MakeCoffeeOrderRequest): Maybe<CoffeeOrder> {
        return Maybe.zip<Barista, Float, CoffeeOrder>(
                findBaristaForCoffeType(request.type),
                priceService.priceForCoffeeType(request.type),
                BiFunction { barista, price ->
                    makeCoffeeOrder(request.requestedBy, request.type, barista, price)
                }
        )
    }

    private fun findBaristaForCoffeType(type: CoffeeType): Maybe<Barista> {
        return baristaService.findAllBaristasForCoffeeType(type)
                .map {
                    if (it.isEmpty())
                        throw BaristaNotFoundException("Barista Not Found for type : $type")
                    else
                        it.firstOrNull()
                }
    }

    private fun makeCoffeeOrder(requestedBy: String, coffeeType: CoffeeType,
                                barista: Barista, price: Float): CoffeeOrder {
        return CoffeeOrder(
                id = UUID.randomUUID(),
                requestedBy = requestedBy,
                barista = barista,
                type = coffeeType,
                price = 0.0F
        )
    }

}


