package core.api.customer

import java.io.Serializable
import java.util.UUID

data class Person(val fistName: String, val lastName: String)

class CustomerId(val identifier: String = UUID.randomUUID().toString()) : Serializable {
    //constructor() : this(UUID.randomUUID().toString())

    override fun toString(): String = identifier
}

enum class CustomerState { CREATED, INACTIVATED }