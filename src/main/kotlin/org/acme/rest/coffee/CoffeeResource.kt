package org.acme.rest.coffee

import org.acme.core.cofffe.CoffeeOrder
import org.acme.core.cofffe.MakeCoffeeOrderRequest
import org.acme.core.cofffe.MakeMeACoffeeInteractor
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


@Path("/coffees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class CoffeeResource(
        @Inject private val makeMeACoffeeInteractor: MakeMeACoffeeInteractor
) {

    @POST
    fun stream(request: MakeCoffeeOrderRequest): CoffeeOrder {
        return makeMeACoffeeInteractor.execute(request).blockingGet()
    }

}