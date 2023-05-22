package app.customer.manager

import core.api.commons.AuditEntry
import core.api.customer.CreateCustomerCommand
import core.api.customer.CustomerCreatedEvent
import core.api.customer.CustomerId
import core.api.customer.CustomerState
import core.api.customer.Person
import java.time.LocalDate
import org.axonframework.messaging.interceptors.BeanValidationInterceptor
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CustomerAggregateTest {

    private lateinit var fixture: FixtureConfiguration<CustomerAggregate>
    private val auditEntry: AuditEntry = AuditEntry("user", LocalDate.now())
    private val customerId: CustomerId = CustomerId("customer-id-123")
    private val person = Person("UserFistName", "UserLastName")


    @BeforeEach
    fun setup() {
        fixture = AggregateTestFixture(CustomerAggregate::class.java)
        fixture.registerCommandDispatchInterceptor(BeanValidationInterceptor())
    }

    @Test
    fun createCustomerTest() {
      val createCustomerCommand = CreateCustomerCommand(customerId, 
                                                        person,
                                                        CustomerState.CREATED, 
                                                        auditEntry)

      val customerCreatedEvent = CustomerCreatedEvent(person, 
                                                      CustomerState.CREATED, 
                                                      customerId, auditEntry)

        fixture.given(customerCreatedEvent)
              .`when`(createCustomerCommand)
              .expectEvents(customerCreatedEvent)
    }

}
