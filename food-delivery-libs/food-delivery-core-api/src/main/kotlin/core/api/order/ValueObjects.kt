package core.api.order

import java.io.Serializable
import java.math.BigDecimal
import java.util.UUID

class CustomerOrderId(val identifier: String) : Serializable {
    constructor() : this(UUID.randomUUID().toString())

    override fun toString(): String = identifier
}

enum class OrderState {
    NONE, CREATED, CONFIRMED, PREPARED, DELIVERED, CANCELLED
}

enum class CustomerOrderState {
    OPEN, CLOSE
}

data class OrderLimit(val amount: Int = 0)

data class Order(val orderId: String, val item: Item, val state: OrderState)

data class Item(val itemId: String, val quantity: Int, val product: List<Product>, val total: Money)

data class Product(val productId: String, val name: String, val price: Money)

data class Money(val amount: BigDecimal = BigDecimal(0)) {

    fun add(delta: Money): Money {
        return Money(amount.add(delta.amount))
    }

    fun multiply(delta: Money): Money {
        return Money(amount.multiply(delta.amount))
    }

    fun isGreaterThanOrEqual(other: Money): Boolean {
        return other.amount >= amount
    }
}
