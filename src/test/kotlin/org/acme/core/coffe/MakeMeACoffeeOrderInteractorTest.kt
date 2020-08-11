package org.acme.core.coffe

import com.nhaarman.mockito_kotlin.*
import io.reactivex.Maybe
import io.reactivex.observers.TestObserver
import org.acme.core.cofffe.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.util.*

@Tag("MakeCoffe")
class MakeMeACoffeeOrderInteractorTest {
    val baristaService: BaristaService = mock()
    val priceService: PriceService = mock()
    val baristaJuanValdez = Barista(id = UUID.randomUUID(), name = "Juan Valdez", specialities = listOf(CoffeeType.ESPRESSO))
    val baristaStarBucks = Barista(id = UUID.randomUUID(), name = "Capuchino Men", specialities = listOf(CoffeeType.ESPRESSO, CoffeeType.CAPPUCCINO))

    @BeforeEach
    open fun setup() {
        baristaService.apply {
            whenever(findAllBaristasForCoffeeType(CoffeeType.ESPRESSO))
                    .thenAnswer { Maybe.just(listOf(baristaJuanValdez)) }
            whenever(findAllBaristasForCoffeeType(CoffeeType.CAPPUCCINO))
                    .thenAnswer { Maybe.just(listOf<Barista>()) }
        }

        priceService.apply {
            whenever(priceForCoffeeType(CoffeeType.ESPRESSO)).thenAnswer { Maybe.just(1.0F) }
            whenever(priceForCoffeeType(CoffeeType.CAPPUCCINO)).thenAnswer { Maybe.just(2.0F) }
            whenever(priceForCoffeeType(CoffeeType.MACCHIATO)).thenAnswer { Exception("PRICE NOT FOUND") }
        }
    }

    @Test
    fun foundBaristaForExpresso() {
        val makeMeACoffeeInteractor = MakeMeACoffeeInteractor(baristaService, priceService)
        val testObserver = TestObserver<CoffeeOrder>()
        makeMeACoffeeInteractor.execute(MakeCoffeeOrderRequest("Jon Doe", CoffeeType.ESPRESSO)).subscribe(testObserver)
        testObserver.assertOf {
            verify(baristaService, times(1)).findAllBaristasForCoffeeType(any())
            verify(priceService, times(1)).priceForCoffeeType(any())
        }
        testObserver.assertComplete()
        testObserver.assertValueCount(1)
        testObserver.values().firstOrNull()?.let {
            assertEquals(CoffeeType.ESPRESSO, it.type)
            assertNotNull(it.barista)
            assertEquals(1.0F, it.price)
        }


    }

    @Test()
    fun notFoundBaristaForMachiato() {
        val makeMeACoffeeInteractor = MakeMeACoffeeInteractor(baristaService, priceService)
        val testObserver = TestObserver<CoffeeOrder>()
        makeMeACoffeeInteractor.execute(MakeCoffeeOrderRequest("Jon Doe", CoffeeType.CAPPUCCINO)).subscribe(testObserver)
        testObserver.assertOf {
            verify(baristaService, times(1)).findAllBaristasForCoffeeType(any())
            verify(priceService, times(1)).priceForCoffeeType(any())
        }
        testObserver.assertError(BaristaNotFoundException::class.java)
        testObserver.assertNoValues()
    }
}