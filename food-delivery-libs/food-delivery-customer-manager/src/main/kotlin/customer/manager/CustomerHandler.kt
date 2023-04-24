package customer.manager

import core.api.customer.CustomerCreatedEvent
import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

@Component
class CustomerHandler(
        private val repository: CustomerRepository,
        private val commandGateway: CommandGateway
) {

    @EventHandler
    //@AllowReplay(value = true)
    fun createCustomer(event: CustomerCreatedEvent) {
        repository.save(CustomerEntity(
                event.aggregateIdentifier.identifier,
                event.name.fistName,
                event.name.lastName,
                event.orderLimit.amount))
    }

    /*  @ResetHandler
      fun onReset() = repository.deleteAll()*/
}