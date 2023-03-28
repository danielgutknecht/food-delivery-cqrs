package customer.manager.domain

import core.api.commons.AuditEntry
import core.api.commons.Money
import core.api.customer.CustomerOrderCreatedEvent
import core.api.customer.CustomerOrderDeliveredEvent
import core.api.customer.MarkCustomerOrderAsDeliveredCommand
import core.api.customer.model.CustomerId
import core.api.customer.model.CustomerOrderId
import org.axonframework.messaging.interceptors.BeanValidationInterceptor
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDate

class CustomerOrderAggregateTest {

    private lateinit var fixture: FixtureConfiguration<CustomerOrder>
    private val auditEntry: AuditEntry = AuditEntry("user", LocalDate.now())
    private val orderId: CustomerOrderId = CustomerOrderId("order-id")
    private val customerId: CustomerId = CustomerId("customer-id")
    private val orderTotal: Money = Money(BigDecimal(1000))


    @Before
    fun setup() {
        fixture = AggregateTestFixture(CustomerOrder::class.java)
        fixture.registerCommandDispatchInterceptor(BeanValidationInterceptor())
    }

    @Test
    fun markOrderAsDeliveredTest() {
        val customerOrderCreatedEvent = CustomerOrderCreatedEvent(orderTotal, customerId, orderId, auditEntry)
        val markCustomerOrderAsDeliveredCommand = MarkCustomerOrderAsDeliveredCommand(orderId, auditEntry)
        val customerOrderDeliveredEvent = CustomerOrderDeliveredEvent(orderId, auditEntry)

        fixture.given(customerOrderCreatedEvent)
            .`when`(markCustomerOrderAsDeliveredCommand)
            .expectEvents(customerOrderDeliveredEvent)

    }
}