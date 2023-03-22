package delivery.common.domain.api.auditable

import delivery.common.domain.api.model.AuditEntry

abstract class AuditableAbstractEvent(open val auditEntry: AuditEntry)