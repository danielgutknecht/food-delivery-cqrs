package app.restaurant.manager


import core.api.order.Order
import core.api.restaurant.CreateRestaurantCommand
import core.api.restaurant.RestaurantCreatedEvent
import core.api.restaurant.RestaurantId
import core.api.restaurant.RestaurantState
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class RestaurantAggregate() {

    @AggregateIdentifier
    private lateinit var id: RestaurantId
    private lateinit var name: String
    private lateinit var orders: Order
    private lateinit var state: RestaurantState


    @CommandHandler
    constructor(command: CreateRestaurantCommand) : this() {
        AggregateLifecycle.apply(
            RestaurantCreatedEvent(
                command.name,
                command.orders,
                command.state,
                command.targetAggregateIdentifier,
                command.auditEntry
            )
        )
    }

    @EventSourcingHandler
    fun on(event: RestaurantCreatedEvent) {
        id = event.aggregateIdentifier
        name = event.name
        orders = event.orders
        state = RestaurantState.OPEN
    }

}