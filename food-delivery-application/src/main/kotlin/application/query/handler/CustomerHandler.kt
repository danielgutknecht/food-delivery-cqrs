package application.query.handler

import application.query.model.CustomerEntity
import application.query.repository.CustomerRepository
import core.api.customer.CustomerCreatedEvent
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.AllowReplay
import org.axonframework.eventhandling.EventHandler
import org.axonframework.eventhandling.ResetHandler
import org.axonframework.eventhandling.SequenceNumber
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Component

@Component
@ProcessingGroup("customer")
internal class CustomerHandler(
    private val repository: CustomerRepository,
    private val messagingTemplate: SimpMessageSendingOperations
) {
    @EventHandler
    @AllowReplay(value = true)
    fun handle(event: CustomerCreatedEvent, @SequenceNumber aggregateVersion: Long) {
        repository.save(
            CustomerEntity(
                event.aggregateIdentifier.identifier,
                aggregateVersion,
                event.name.fistName,
                event.name.lastName,
                event.orderLimit.amount
            )
        )
        broadcastUpdates()
    }

    @ResetHandler
    fun onReset() = repository.deleteAll()

    private fun broadcastUpdates() = messagingTemplate.convertAndSend("/topic/customers.updates", repository.findAll())
}