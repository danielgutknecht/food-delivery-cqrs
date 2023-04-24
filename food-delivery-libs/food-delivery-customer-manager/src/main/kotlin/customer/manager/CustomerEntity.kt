package customer.manager

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.math.BigDecimal


@Entity(name = "customer")
class CustomerEntity(
        @Id
        var id: String,
        var firstName: String,
        var lastName: String,
        var orderLimit: BigDecimal
)