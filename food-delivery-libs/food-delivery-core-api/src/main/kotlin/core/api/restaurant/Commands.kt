package core.api.restaurant

import core.api.commons.AuditEntry
import core.api.commons.auditable.AuditableAbstractCommand
import core.api.order.Order
import org.axonframework.modelling.command.TargetAggregateIdentifier


abstract class RestaurantCommand(
    open val targetAggregateIdentifier: RestaurantId,
    override val auditEntry: AuditEntry
) : AuditableAbstractCommand(auditEntry)

data class CreateRestaurantCommand(
    @TargetAggregateIdentifier
    override val targetAggregateIdentifier: RestaurantId,
    val name: String,
    val orders: Order,
    val state: RestaurantState,
    override val auditEntry: AuditEntry
) : RestaurantCommand(targetAggregateIdentifier, auditEntry) {
    /* constructor(name: String, orders: Order, state: RestaurantState, auditEntry: AuditEntry)
             : this(name, orders, state, RestaurantId(), auditEntry)*/
}
