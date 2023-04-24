package core.api.customer

import core.api.commons.auditable.AuditableAbstractEvent
import core.api.commons.AuditEntry
import core.api.commons.Money
import core.api.commons.Person
import core.api.customer.model.CustomerId
import core.api.customer.model.CustomerOrderId

abstract class CustomerEvent(open val aggregateIdentifier: CustomerId, override val auditEntry: AuditEntry) :
    AuditableAbstractEvent(auditEntry)

abstract class CustomerOrderEvent(open val aggregateIdentifier: CustomerOrderId, override val auditEntry: AuditEntry) :
    AuditableAbstractEvent(auditEntry)

data class CustomerCreatedEvent(
        val name: Person,
        val orderLimit: Money,
        override val aggregateIdentifier: CustomerId,
        override val auditEntry: AuditEntry
) : CustomerEvent(aggregateIdentifier, auditEntry)

data class CustomerOrderCreatedEvent(
    val orderTotal: Money,
    override val aggregateIdentifier: CustomerId,
    val customerOrderId: CustomerOrderId,
    override val auditEntry: AuditEntry
) : CustomerEvent(aggregateIdentifier, auditEntry)

data class CustomerOrderDeliveredEvent(
    override val aggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry
) : CustomerOrderEvent(aggregateIdentifier, auditEntry)

data class CustomerOrderRejectedEvent(
    override val aggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry
) : CustomerOrderEvent(aggregateIdentifier, auditEntry)

