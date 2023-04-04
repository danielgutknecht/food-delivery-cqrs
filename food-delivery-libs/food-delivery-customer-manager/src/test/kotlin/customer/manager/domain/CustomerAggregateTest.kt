package customer.manager.domain

import core.api.commons.AuditEntry
import core.api.commons.Money
import core.api.commons.PersonName
import core.api.customer.CreateCustomerCommand
import core.api.customer.CreateCustomerOrderCommand
import core.api.customer.CustomerCreatedEvent
import core.api.customer.CustomerOrderCreatedEvent
import core.api.customer.model.CustomerId
import core.api.customer.model.CustomerOrderId
import org.axonframework.messaging.interceptors.BeanValidationInterceptor
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class CustomerAggregateTest {

    private lateinit var fixture: FixtureConfiguration<Customer>
    private val auditEntry: AuditEntry = AuditEntry("user", LocalDate.now())
    private val customerId: CustomerId = CustomerId("customer-id")
    private val orderId: CustomerOrderId = CustomerOrderId("order-id")
    private val orderLimit: Money = Money(BigDecimal.valueOf(1000))

    @BeforeEach
    fun setup() {
        fixture = AggregateTestFixture(Customer::class.java)
        fixture.registerCommandDispatchInterceptor(BeanValidationInterceptor())
    }

    @Test
    fun createCustomerTest() {
        val name = PersonName("UserFistName", "UserLastName")
        val createCustomerCommand = CreateCustomerCommand(name, orderLimit, auditEntry)
        val customerCreatedEvent =
            CustomerCreatedEvent(name, orderLimit, createCustomerCommand.targetAggregateIdentifier, auditEntry)

        fixture.given(customerCreatedEvent).`when`(createCustomerCommand).expectEvents(customerCreatedEvent)
    }

    @Test
    fun createCustomerOrderTest() {
        val name = PersonName("UserFistName", "UserLastName")
        val createCustomerOrderCommand = CreateCustomerOrderCommand(customerId,orderId, orderLimit, auditEntry)
        val customerCreatedEvent = CustomerCreatedEvent(name, orderLimit.add(Money(BigDecimal.ONE)), createCustomerOrderCommand.targetAggregateIdentifier, auditEntry)
        val customerOrderCreatedEvent = CustomerOrderCreatedEvent(orderLimit, customerId, createCustomerOrderCommand.customerOrderId, auditEntry)

        fixture.given(customerCreatedEvent)
            .`when`(createCustomerOrderCommand)
            .expectEvents(customerOrderCreatedEvent)
    }

    @Test
    fun createCustomerOrderFailOrderLimitTest() {
        val name = PersonName("UserFistName", "UserLastName")
        val createCustomerOrderCommand = CreateCustomerOrderCommand(customerId,orderId, orderLimit, auditEntry)
        val customerCreatedEvent = CustomerCreatedEvent(name, orderLimit, createCustomerOrderCommand.targetAggregateIdentifier, auditEntry)
        val customerOrderCreatedEvent = CustomerOrderCreatedEvent(orderLimit, customerId, createCustomerOrderCommand.customerOrderId, auditEntry)

        fixture.given(customerCreatedEvent)
            .`when`(createCustomerOrderCommand)
            .expectException(UnsupportedOperationException::class.java)
    }

}