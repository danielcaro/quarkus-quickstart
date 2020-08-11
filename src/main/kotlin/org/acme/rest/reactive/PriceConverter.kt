package org.acme.rest.reactive

import io.smallrye.reactive.messaging.annotations.Broadcast
import org.eclipse.microprofile.reactive.messaging.Incoming
import org.eclipse.microprofile.reactive.messaging.Outgoing
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class PriceConverter {

    @Incoming("prices")
    @Outgoing("my-data-stream")
    @Broadcast
    fun process(priceInUsd: Int): Double {
        return priceInUsd *  0.01
    }

}