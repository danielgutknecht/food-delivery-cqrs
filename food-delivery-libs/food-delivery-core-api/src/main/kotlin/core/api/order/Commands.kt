package core.api.order

import core.api.commons.AuditEntry
import core.api.commons.auditable.AuditableAbstractCommand
import core.api.customer.CustomerId
import core.api.restaurant.RestaurantId
import org.axonframework.modelling.command.TargetAggregateIdentifier

abstract class OrderCommand(
    open val targetAggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry
) : AuditableAbstractCommand(auditEntry)


data class CreateOrderCommand(
    @TargetAggregateIdentifier
    override val targetAggregateIdentifier: CustomerOrderId,
    val restaurant: RestaurantId,
    val customerId: CustomerId,
    val item: Item,
    val orderLimit: OrderLimit,
    val state: OrderState,
    override val auditEntry: AuditEntry
) : OrderCommand(targetAggregateIdentifier, auditEntry) {
     /*constructor(targetAggregateIdentifier: RestaurantId, orderDetails: OrderDetails, auditEntry: AuditEntry)
             : this(targetAggregateIdentifier, orderDetails, customerId, auditEntry)*/
}

data class MarkOrderAsPreparedCommand(
    @TargetAggregateIdentifier
    override val targetAggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry
) : OrderCommand(targetAggregateIdentifier, auditEntry)
