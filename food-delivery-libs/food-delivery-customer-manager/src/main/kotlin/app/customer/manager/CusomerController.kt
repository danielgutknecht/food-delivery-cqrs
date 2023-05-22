package app.customer.manager

import app.model.CustomerEntity
import core.api.commons.AuditEntry
import core.api.customer.CreateCustomerCommand
import core.api.customer.CustomerId
import core.api.customer.CustomerState
import core.api.customer.Person
import java.math.BigDecimal
import java.time.LocalDate
import java.util.concurrent.CompletableFuture
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping(
    "/customers",
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class CustomerController(
    val commandGateway: CommandGateway,
    val customerRepository: CustomerRepository
) {

    companion object {
        val auditEntry: AuditEntry = AuditEntry("TEST", LocalDate.now())
    }

    @PostMapping
    fun createCustomer(@RequestBody request: CreateCustomerDTO): CompletableFuture<CustomerEntity> {
        return commandGateway.send(
            CreateCustomerCommand(
                CustomerId(),
                Person(request.firstName, request.lastName),
                CustomerState.CREATED,
                auditEntry
            )
        )
    }

    @GetMapping
    fun allCustomers(): Iterable<CustomerEntity> = customerRepository.findAll()

    @GetMapping("/customers/{id}")
    fun getCustomer(@PathVariable id: String) = customerRepository.findById(id)

}

data class CreateCustomerDTO(val firstName: String, val lastName: String, val orderLimit: BigDecimal)
