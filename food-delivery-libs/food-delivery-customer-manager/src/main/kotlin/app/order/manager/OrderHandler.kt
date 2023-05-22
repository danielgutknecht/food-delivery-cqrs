package app.order.manager


import ItemRepository
import OrderRepository
import app.customer.manager.CustomerRepository
import app.model.CustomerEntity
import app.model.ItemEntity
import app.model.OrderEntity
import app.model.RestaurantEntity
import app.restaurant.manager.RestaurantRepository
import core.api.order.OrderCreatedEvent
import kotlin.jvm.optionals.toList
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class OrderHandler {


    lateinit var orderRepository: OrderRepository
    lateinit var restaurantRepository: RestaurantRepository
    lateinit var customerRepository: CustomerRepository
    lateinit var itemRepository: ItemRepository

    fun findRestaurantById(id: String): RestaurantEntity {
        return restaurantRepository.findById(id).get()
    }

    fun findCustomerById(id: String): CustomerEntity {
        return customerRepository.findById(id).get()
    }

    fun findItemById(id: String): List<ItemEntity> {
        return itemRepository.findById(id).toList()
    }


    @EventHandler
    fun createRestaurantOrder(event: OrderCreatedEvent) {
        orderRepository.save(
            OrderEntity(
                event.aggregateIdentifier.identifier,
                event.orderLimit.amount,
                findRestaurantById(event.restaurant.identifier),
                findCustomerById(event.customerId.identifier),
                findItemById(event.item.itemId),
                event.state
            )
        )
    }
}
