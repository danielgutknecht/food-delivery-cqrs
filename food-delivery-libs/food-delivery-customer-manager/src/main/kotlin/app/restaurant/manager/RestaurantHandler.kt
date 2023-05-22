package app.restaurant.manager

import app.model.RestaurantEntity
import core.api.restaurant.RestaurantCreatedEvent
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class RestaurantHandler(
    val restaurantRepository: RestaurantRepository
) {

    @EventHandler
    fun createRestaurant(event: RestaurantCreatedEvent) {

        restaurantRepository.save(
            RestaurantEntity(
                event.aggregateIdentifier.identifier,
                event.name,
                event.state,
            )
        )
    }
}