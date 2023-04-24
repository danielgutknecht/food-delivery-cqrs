package customer.manager

import core.api.commons.AuditEntry
import core.api.commons.Money
import core.api.commons.Person
import core.api.customer.CreateCustomerCommand
import core.api.customer.model.CustomerId
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.concurrent.CompletableFuture

@CrossOrigin("*")
@RestController
@RequestMapping("/customers", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
class CustomerController(
        val commandGateway: CommandGateway,
        val customerRepository: CustomerRepository
) {

    companion object {
        val auditEntry: AuditEntry = AuditEntry("TEST", LocalDate.now())
    }

    @PostMapping
    fun createCustomer(@RequestBody request: CreateCustomerDTO): CompletableFuture<CustomerEntity> {
        return commandGateway.send(CreateCustomerCommand(CustomerId(),
                Person(request.firstName, request.lastName),
                Money(request.orderLimit),
                auditEntry))
    }

    @GetMapping
    fun allCustomers(): Iterable<CustomerEntity> = customerRepository.findAll()

    @GetMapping("/customers/{id}")
    fun getCustomer(@PathVariable id: String) = customerRepository.findById(id)

}
data class CreateCustomerDTO(val firstName: String, val lastName: String, val orderLimit: BigDecimal)