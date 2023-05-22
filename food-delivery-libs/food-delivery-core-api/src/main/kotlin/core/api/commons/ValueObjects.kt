package core.api.commons

import java.time.LocalDate

data class AuditEntry(val who: String, val `when`: LocalDate)

