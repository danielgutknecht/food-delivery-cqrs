package core.api.restaurant

import java.io.Serializable
import java.util.UUID


enum class RestaurantState {
    OPEN, CLOSE, ACTIVE, INATIVATED
}

data class RestaurantId(val identifier: String = UUID.randomUUID().toString()) : Serializable {
    override fun toString(): String = identifier
}
