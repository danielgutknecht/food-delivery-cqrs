package core.api.customer

import core.api.commons.AuditEntry
import core.api.commons.auditable.AuditableAbstractCommand
import jakarta.validation.Valid
import org.axonframework.modelling.command.TargetAggregateIdentifier


abstract class CustomerCommand(
    open val targetAggregateIdentifier: CustomerId,
    override val auditEntry: AuditEntry
) : AuditableAbstractCommand(auditEntry)


data class CreateCustomerCommand(
    @TargetAggregateIdentifier
    override val targetAggregateIdentifier: CustomerId,
    @field:Valid
    val person: Person,
    val state: CustomerState,
    override val auditEntry: AuditEntry
) : CustomerCommand(targetAggregateIdentifier, auditEntry) /*{
    constructor(person: Person, state: CustomerState, auditEntry: AuditEntry) : this(
        CustomerId(),
        person,
        state,
        auditEntry
    )
}*/