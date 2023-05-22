package app.order.manager

import core.api.customer.CustomerId
import core.api.order.CreateOrderCommand
import core.api.order.CustomerOrderId
import core.api.order.Item
import core.api.order.OrderCreatedEvent
import core.api.order.OrderState
import core.api.order.OrderLimit
import core.api.restaurant.RestaurantId
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
internal class OrderAggregate(){

  /*  companion object {
        val logger = LogManager.getLogManager()
    }
*/

    @AggregateIdentifier
    lateinit var orderId: CustomerOrderId
    lateinit var restaurantId: RestaurantId
    lateinit var customerId: CustomerId
    lateinit var item: Item
    lateinit var orderLimit: OrderLimit
    lateinit var state: OrderState


    @CommandHandler
    constructor(command: CreateOrderCommand) : this() {
        AggregateLifecycle.apply(
            OrderCreatedEvent(
                command.targetAggregateIdentifier,
                command.restaurant,
                command.customerId,
                command.item,
                command.orderLimit,
                command.state,
                command.targetAggregateIdentifier,
                command.auditEntry
            )
        )
    }

    @EventSourcingHandler
    fun on(event: OrderCreatedEvent) {
        orderId = event.aggregateIdentifier
        restaurantId = event.restaurant
        customerId = event.customerId
        item = event.item
        orderLimit = event.orderLimit
        state = OrderState.CREATED
    }


    /* @CommandHandler
     fun constructor(command: CreateOrderCommand) {
         // if (command.orderTotal.isGreaterThanOrEqual(orderList))
         AggregateLifecycle.apply(
             OrderCreatedEvent(
                 command.orderTotal,
                 command.targetAggregateIdentifier,
                 command.customerOrderId,
                 command.auditEntry
             )
         )
         //else throw UnsupportedOperationException("Customer limit is reached")
     }

     @CommandHandler
     fun handle(command: CreateOrderCommand) {
         if (orders.orderId.isNotEmpty()) {
             AggregateLifecycle.createNew(RestaurantOrder::class.java) { RestaurantOrder(command) }
         } else throw UnsupportedOperationException("Pedido Inválido - não existe no menu")
     }


     @CommandHandler
     fun handle(command: MarkOrderAsPreparedCommand) {
         if (OrderState.CREATED == state) {
             AggregateLifecycle.apply(
                 OrderPreparedEvent(
                     command.targetAggregateIdentifier,
                     command.auditEntry
                 )
             )
         } else throw UnsupportedOperationException("O estado não é CREATED")
     }

     @EventSourcingHandler
     fun on(event: OrderPreparedEvent) {
         state = OrderState.PREPARED
     }


     @CommandHandler
     fun handle(command: MarkCustomerOrderAsDeliveredCommand) {
         if (CustomerOrderState.CREATED == state)
             AggregateLifecycle.apply(CustomerOrderDeliveredEvent(command.targetAggregateIdentifier, command.auditEntry))
         else throw UnsupportedOperationException("the current state is not created")
     }

     @EventSourcingHandler
     fun on(event: CustomerOrderDeliveredEvent) {
         state = CustomerOrderState.DELIVERED
     }

     */
}
