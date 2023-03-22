package delivery.common.domain.api.model

import delivery.common.domain.api.auditable.AuditableAbstractEvent

abstract class CustomerEvent(open val aggregateIdentifier: CustomerId, override val auditEntry: AuditEntry) :
    AuditableAbstractEvent(auditEntry)

abstract class CustomerOrderEvent(open val aggregateIdentifier: CustomerOrderId, override val auditEntry: AuditEntry) :
    AuditableAbstractEvent(auditEntry)

data class CustomerCreatedEvent(
    val name: PersonName,
    val orderLimit: Money,
    override val aggregateIdentifier: CustomerId,
    override val auditEntry: AuditEntry
) : CustomerEvent(aggregateIdentifier, auditEntry)

data class CustomerOrderCreateEvent(
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

