package customer.manager.domain

import core.api.commons.Money
import core.api.commons.PersonName
import core.api.customer.CreateCustomerCommand
import core.api.customer.CreateCustomerOrderCommand
import core.api.customer.CustomerCreatedEvent
import core.api.customer.CustomerOrderCreatedEvent
import core.api.customer.model.CustomerId
import core.api.noarg.NoArg
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.lang.UnsupportedOperationException

@Aggregate
@NoArg
internal data class Customer(
    @AggregateIdentifier
    var id: CustomerId,
    var name: PersonName,
    var orderList: Money
) {

    /*@AggregateIdentifier
    lateinit var id: CustomerId
    lateinit var name: PersonName
    lateinit var orderList: Money*/

    @CommandHandler
    fun handle(command: CreateCustomerCommand) {
        AggregateLifecycle.apply(
            CustomerCreatedEvent(
                command.name,
                command.orderLimit,
                command.targetAggregateIdentifier,
                command.auditEntry
            )
        )
    }

    @EventSourcingHandler
    fun on(event: CustomerCreatedEvent) {
        id = event.aggregateIdentifier
        name = event.name
        orderList = event.orderLimit
    }

    @CommandHandler
    fun handle(command: CreateCustomerOrderCommand) {
        if (!command.orderTotal.isGreaterThanOrEqual(orderList))
            AggregateLifecycle.apply(
                CustomerOrderCreatedEvent(
                    command.orderTotal,
                    command.targetAggregateIdentifier,
                    command.customerOrderId,
                    command.auditEntry
                )
            )
        else throw UnsupportedOperationException("Customer limit is reached")
    }

}

