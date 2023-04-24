package core.api.commons.auditable

import core.api.commons.AuditEntry

abstract class AuditableAbstractCommand(open val auditEntry: AuditEntry)