package app.customer.manager

import app.model.CustomerEntity
import core.api.customer.CustomerCreatedEvent
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class CustomerHandler(
    private val customerRepository: CustomerRepository
) {

    @EventHandler
    fun createCustomer(event: CustomerCreatedEvent) {
        customerRepository.save(
            CustomerEntity(
                event.aggregateIdentifier.identifier,
                event.person.fistName,
                event.person.lastName,
            )
        )
    }
}