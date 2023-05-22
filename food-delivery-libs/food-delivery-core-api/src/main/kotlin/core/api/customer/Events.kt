package core.api.customer

import core.api.commons.AuditEntry
import core.api.commons.auditable.AuditableAbstractEvent

abstract class CustomerEvent(open val aggregateIdentifier: CustomerId, override val auditEntry: AuditEntry) :
    AuditableAbstractEvent(auditEntry)

data class CustomerCreatedEvent(
    val person: Person,
    val state: CustomerState,
    override val aggregateIdentifier: CustomerId,
    override val auditEntry: AuditEntry
) : CustomerEvent(aggregateIdentifier, auditEntry)

