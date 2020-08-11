package org.acme.rest

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.acme.core.cofffe.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.*
import javax.inject.Inject
import javax.ws.rs.core.Response

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoffeeResourceTest {

    val baristaJuanValdez = Barista(id = UUID.randomUUID(), name = "Juan Valdez", specialities = listOf(CoffeeType.ESPRESSO))
    val baristaStarBucks = Barista(id = UUID.randomUUID(), name = "Capuchino Men", specialities = listOf(CoffeeType.ESPRESSO, CoffeeType.CAPPUCCINO))

    @Inject
    lateinit var baristaService: BaristaService

    @BeforeAll
    internal fun setup() {
        baristaService.create(baristaJuanValdez).subscribe()
        baristaService.create(baristaStarBucks).subscribe()
    }

    @Test
    fun testMakeMeACoffee() {
        given().contentType("application/json").body(MakeCoffeeOrderRequest("Jon Doe", CoffeeType.ESPRESSO))
                .`when`().post("/coffees")
                .then().statusCode(Response.Status.OK.statusCode).extract().`as`(CoffeeOrder::class.java)
                .let {
                    assertEquals(it.type, CoffeeType.ESPRESSO)
                    assertEquals(it.price, 1.0F)
                }
    }

    @Test
    fun testMakeMeACoffeeNotFound() {
        given().contentType("application/json").body(MakeCoffeeOrderRequest("Jon Doe", CoffeeType.MACCHIATO))
                .`when`().post("/coffees")
                .then()
                .statusCode(Response.Status.FORBIDDEN.statusCode)
    }

}