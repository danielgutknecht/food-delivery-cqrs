package core.api.customer.model

import java.io.Serializable
import java.util.UUID

enum class CustomerOrderState {
    CREATED, DELIVERED, CANCELLED
}

enum class CustomerState{CREATED}

class CustomerId(val identifier: String = UUID.randomUUID().toString()) : Serializable {
    //constructor() : this(UUID.randomUUID().toString())

    override fun toString(): String = identifier
}

class CustomerOrderId(val identifier: String) : Serializable {
    constructor() : this(UUID.randomUUID().toString())

    override fun toString(): String = identifier
}