package delivery.common.domain.api.model

import java.io.Serializable

class CustomerId(val identifier: String) : Serializable {

    override fun toString(): String = identifier
}

class CustomerOrderId(val identifier: String) : Serializable {

    override fun toString(): String = identifier
}