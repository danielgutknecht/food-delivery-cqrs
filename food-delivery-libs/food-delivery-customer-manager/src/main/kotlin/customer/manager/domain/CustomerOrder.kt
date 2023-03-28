package customer.manager.domain

import core.api.commons.Money
import core.api.customer.CreateCustomerOrderCommand
import core.api.customer.CustomerOrderCreatedEvent
import core.api.customer.CustomerOrderDeliveredEvent
import core.api.customer.MarkCustomerOrderAsDeliveredCommand
import core.api.customer.model.CustomerId
import core.api.customer.model.CustomerOrderId
import core.api.customer.model.CustomerOrderState
import core.api.noarg.NoArg
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import java.math.BigDecimal

@Aggregate
@NoArg
internal data class CustomerOrder(
    @AggregateIdentifier
    var id: CustomerOrderId,
    var customerId: CustomerId,
    var state: CustomerOrderState,
    var orderTotal: Money
) {

    val customerOrder = CustomerOrder::class.constructors


    //private Logger logger = LogManager.getLogger(TaskCreatedEvent.class);

    /*@AggregateIdentifier
    private lateinit var id: CustomerOrderId
    private lateinit var customerId: CustomerId
    private lateinit var state: CustomerOrderState
    private lateinit var orderTotal: Money*/


    @CommandHandler
    fun handle(command: CreateCustomerOrderCommand) {
        AggregateLifecycle.apply(
            CustomerOrderCreatedEvent(
                command.orderTotal,
                command.targetAggregateIdentifier,
                command.customerOrderId,
                command.auditEntry
            )
        )
    }

    @EventSourcingHandler
    fun on(event: CustomerOrderCreatedEvent) {
        id = event.customerOrderId
        customerId = event.aggregateIdentifier
        orderTotal = event.orderTotal
        state = CustomerOrderState.CREATED
    }

    @CommandHandler
    fun handle(command: MarkCustomerOrderAsDeliveredCommand) {
        if (CustomerOrderState.CREATED == state) {
            AggregateLifecycle.apply(
                CustomerOrderDeliveredEvent(
                    command.targetAggregateIdentifier,
                    command.auditEntry
                )
            )
        }
    }

    @EventSourcingHandler
    fun on(event: CustomerOrderDeliveredEvent) {
        state = CustomerOrderState.DELIVERED
    }
}