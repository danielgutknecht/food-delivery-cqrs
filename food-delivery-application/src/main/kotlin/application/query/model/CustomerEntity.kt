package application.query.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal


@Entity(name = "customer")
class CustomerEntity(
    @Id
    var id: String,
    var aggregateVersion: Long,
    var firstName: String,
    var lastName: String,
    var orderLimit: BigDecimal
)