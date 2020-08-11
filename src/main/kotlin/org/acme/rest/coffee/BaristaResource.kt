package org.acme.rest.coffee

import org.acme.core.cofffe.*
import java.util.*
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType


@Path("/baristas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class BaristaResource(
        @Inject private val baristaService: BaristaService
) {

    @GET
    fun allBaristas(): List<Barista> {
        return baristaService.findAll().blockingGet()
    }

    @GET
    @Path("/by_coffee_type/{coffeeType}")
    fun allForCoffeType(@PathParam("coffeeType") coffeeType: CoffeeType): List<Barista> {
        return baristaService.findAllBaristasForCoffeeType(coffeeType).blockingGet()
    }

    @GET
    @Path("/clear")
    fun dropAll(): List<Barista> {
        baristaService.dropAll()
        return listOf()
    }

    @GET
    @Path("/start")
    fun start(): List<Barista> {
        baristaService.create(Barista(id = UUID.randomUUID(), name = "Juan Valdez 2", specialities = listOf(CoffeeType.MACCHIATO))).blockingGet()
        baristaService.create(Barista(id = UUID.randomUUID(), name = "Juan Valdez", specialities = listOf(CoffeeType.ESPRESSO))).blockingGet()
        return baristaService.findAll().blockingGet()
    }


}