package core.api.order

import core.api.commons.AuditEntry
import core.api.commons.auditable.AuditableAbstractEvent
import core.api.customer.CustomerId
import core.api.restaurant.RestaurantId

abstract class OrderEvent(open val aggregateIdentifier: CustomerOrderId, override val auditEntry: AuditEntry) :
    AuditableAbstractEvent(auditEntry)

data class OrderCreatedEvent(
    val orderId: CustomerOrderId,
    val restaurant: RestaurantId,
    val customerId: CustomerId,
    val item: Item,
    val orderLimit: OrderLimit,
    val state: OrderState,
    override val aggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry
) : OrderEvent(aggregateIdentifier, auditEntry)

data class OrderDeliveredEvent(
    override val aggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry
) : OrderEvent(aggregateIdentifier, auditEntry)

data class OrderRejectedEvent(
    override val aggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry
) : OrderEvent(aggregateIdentifier, auditEntry)

data class OrderPreparedEvent(
    override val aggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry
) : OrderEvent(aggregateIdentifier, auditEntry)

