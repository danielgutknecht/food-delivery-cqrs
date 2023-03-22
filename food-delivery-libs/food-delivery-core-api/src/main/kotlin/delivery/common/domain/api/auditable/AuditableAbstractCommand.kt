package delivery.common.domain.api.auditable

import delivery.common.domain.api.model.AuditEntry

abstract class AuditableAbstractCommand(open val auditEntry: AuditEntry) {
}