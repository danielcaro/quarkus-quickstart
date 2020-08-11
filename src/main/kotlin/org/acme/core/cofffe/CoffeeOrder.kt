package org.acme.core.cofffe

import org.acme.core.cofffe.CoffeeType
import java.util.*

class CoffeeOrder(
        val id: UUID,
        val requestedBy: String,
        val barista: Barista,
        val type: CoffeeType,
        val price: Float
)