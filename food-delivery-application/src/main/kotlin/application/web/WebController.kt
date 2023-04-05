package application.web

import application.query.model.CustomerEntity
import application.query.repository.CustomerRepository
import core.api.commons.AuditEntry
import core.api.commons.Money
import core.api.commons.PersonName
import core.api.customer.CreateCustomerCommand
import org.axonframework.commandhandling.callbacks.LoggingCallback
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.stereotype.Controller
import java.math.BigDecimal
import java.time.LocalDate

@Controller
class WebController(
    val commandGateway: CommandGateway,
    private val customerRepository: CustomerRepository,
    val auditEntry: AuditEntry = AuditEntry("TEST", LocalDate.now())
) {

    fun createCustomer(request: CreateCustomerDTO) {
        commandGateway.send(
            CreateCustomerCommand(
                PersonName(request.firstName, request.lastName),
                Money(request.orderLimit),
                auditEntry
            ), LoggingCallback.INSTANCE
        )
    }

    @SubscribeMapping("/customers")
    fun allCustomers(): Iterable<CustomerEntity> = customerRepository.findAll()

    @SubscribeMapping("/customers/{id}")
    fun getCustomer(@DestinationVariable id: String) = customerRepository.findById(id)

}

data class CreateCustomerDTO(val firstName: String, val lastName: String, val orderLimit: BigDecimal)