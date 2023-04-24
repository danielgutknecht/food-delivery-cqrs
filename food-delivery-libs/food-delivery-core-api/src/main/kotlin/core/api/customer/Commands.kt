package core.api.customer

import core.api.commons.AuditEntry
import core.api.commons.Money
import core.api.commons.Person
import core.api.commons.auditable.AuditableAbstractCommand
import core.api.customer.model.CustomerId
import core.api.customer.model.CustomerOrderId
import jakarta.validation.Valid
import org.axonframework.modelling.command.TargetAggregateIdentifier


abstract class CustomerCommand(
    open val targetAggregateIdentifier: CustomerId,
    override val auditEntry: AuditEntry
) : AuditableAbstractCommand(auditEntry)

abstract class CustomerOrderCommand(
    open val targetAggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry
) : AuditableAbstractCommand(auditEntry)

data class CreateCustomerCommand(
        @TargetAggregateIdentifier
    override val targetAggregateIdentifier: CustomerId,
        @field:Valid
    val name: Person,
        val orderLimit: Money,
        override val auditEntry: AuditEntry
) : CustomerCommand(targetAggregateIdentifier, auditEntry) {
    constructor(name: Person, orderLimit: Money, auditEntry: AuditEntry) : this(
        CustomerId(),
        name,
        orderLimit,
        auditEntry
    )
}

data class CreateCustomerOrderCommand(
    @TargetAggregateIdentifier
    override val targetAggregateIdentifier: CustomerId,
    val customerOrderId: CustomerOrderId,
    @field:Valid
    val orderTotal: Money,
    override val auditEntry: AuditEntry
) : CustomerCommand(targetAggregateIdentifier, auditEntry)

data class MarkCustomerOrderAsDeliveredCommand(
    @TargetAggregateIdentifier
    override val targetAggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry
) : CustomerOrderCommand(targetAggregateIdentifier, auditEntry)