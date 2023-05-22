package core.api.restaurant

import core.api.commons.AuditEntry
import core.api.commons.auditable.AuditableAbstractEvent
import core.api.order.Order

abstract class RestaurantEvent(open val aggregateIdentifier: RestaurantId, override val auditEntry: AuditEntry) :
    AuditableAbstractEvent(auditEntry)

data class RestaurantCreatedEvent(
    val name: String,
    val orders: Order,
    val state: RestaurantState,
    override val aggregateIdentifier: RestaurantId,
    override val auditEntry: AuditEntry
) : RestaurantEvent(aggregateIdentifier, auditEntry)


