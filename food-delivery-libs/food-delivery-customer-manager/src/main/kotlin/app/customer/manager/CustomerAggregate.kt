package app.customer.manager

import core.api.customer.CreateCustomerCommand
import core.api.customer.CustomerCreatedEvent
import core.api.customer.CustomerId
import core.api.customer.Person
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
internal class CustomerAggregate() {

    @AggregateIdentifier
    lateinit var id: CustomerId
    lateinit var person: Person

    @CommandHandler
    constructor(command: CreateCustomerCommand) : this() {
        AggregateLifecycle.apply(
            CustomerCreatedEvent(
                command.person,
                command.state,
                command.targetAggregateIdentifier,
                command.auditEntry
            )
        )
    }

    @EventSourcingHandler
    fun on(event: CustomerCreatedEvent) {
        id = event.aggregateIdentifier
        person = event.person
    }

}

