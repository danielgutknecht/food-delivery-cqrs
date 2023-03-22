package delivery.common.domain.api.model

import delivery.common.domain.api.auditable.AuditableAbstractCommand
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
    val name: PersonName,
    val orderLimit: Money,
    override val auditEntry: AuditEntry
) : CustomerCommand(targetAggregateIdentifier, auditEntry)

data class CreateCustomerOrderCommand(
    @TargetAggregateIdentifier
    override val targetAggregateIdentifier: CustomerOrderId,
    val customerOrderId: CustomerOrderId,
    @field:Valid
    val orderTotal: Money,
    override val auditEntry: AuditEntry
) : CustomerOrderCommand(targetAggregateIdentifier, auditEntry)

data class MarkCustomerOrderAsDeliveredCommand(
    override val targetAggregateIdentifier: CustomerOrderId,
    override val auditEntry: AuditEntry
) : CustomerOrderCommand(targetAggregateIdentifier, auditEntry)