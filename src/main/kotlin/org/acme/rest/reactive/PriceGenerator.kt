package org.acme.rest.reactive

import io.reactivex.Flowable
import org.acme.core.cofffe.PriceService
import org.eclipse.microprofile.reactive.messaging.Outgoing
import java.util.concurrent.TimeUnit
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject


@ApplicationScoped
class PriceGenerator(
        @Inject val priceService: PriceService
) {

    @Outgoing("generated-price")
    fun generate(): Flowable<Int> {
        return Flowable
                .interval(1, TimeUnit.SECONDS)
                .onBackpressureDrop()
                .map { priceService.randomPrice() }
    }
}