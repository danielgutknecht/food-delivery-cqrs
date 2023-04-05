package core.api.commons

import java.math.BigDecimal
import java.time.LocalDate

data class AuditEntry(val who: String, val `when`: LocalDate)

data class PersonName(val fistName: String, val lastName: String)

data class Money(val amount: BigDecimal) {

    constructor() : this(BigDecimal(0))

    fun add(delta: Money): Money {
        return Money(amount.add(delta.amount))
    }

    fun multiply(delta: Money): Money {
        return Money(amount.multiply(delta.amount))
    }

    fun isGreaterThanOrEqual(other: Money): Boolean {
        return amount >= other.amount
    }

}