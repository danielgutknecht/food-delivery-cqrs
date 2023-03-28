package core.api.commons.auditable

import core.api.commons.AuditEntry

abstract class AuditableAbstractEvent(open val auditEntry: AuditEntry)