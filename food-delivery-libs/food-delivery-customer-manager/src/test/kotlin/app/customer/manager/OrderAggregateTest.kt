package app.customer.manager


import app.order.manager.OrderAggregate
import core.api.commons.AuditEntry
import core.api.customer.CustomerId
import core.api.order.CreateOrderCommand
import core.api.order.CustomerOrderId
import core.api.order.Item
import core.api.order.OrderCreatedEvent
import core.api.order.OrderState
import core.api.order.Product
import core.api.order.Money
import core.api.order.OrderLimit
import core.api.restaurant.RestaurantId
import java.math.BigDecimal
import java.time.LocalDate
import org.axonframework.messaging.interceptors.BeanValidationInterceptor
import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class OrderAggregateTest {

    private lateinit var fixture: FixtureConfiguration<OrderAggregate>
    private val auditEntry: AuditEntry = AuditEntry("user", LocalDate.now())
    private val customerOrderId: CustomerOrderId = CustomerOrderId("order-id-123")
    private val customerId: CustomerId = CustomerId("customer-id")
    private val orderTotal: Money = Money(BigDecimal(1000))
    private val restaurantId: RestaurantId = RestaurantId("restaurante-123")
    private val price: Money = Money(BigDecimal(29.90))
    private val orderLimit: OrderLimit = OrderLimit(50)
    private val product = mutableListOf<Product>(Product("product-222", "prod-name", price))
    private val item = Item("item-111", 3, product, orderTotal)


    @BeforeEach
    fun setup() {
        fixture = AggregateTestFixture(OrderAggregate::class.java)
        fixture.registerCommandDispatchInterceptor(BeanValidationInterceptor())
    }

    @Test
    fun createCustomerOrderTest() {

        val createCustomerOrderCommand =
            CreateOrderCommand(
                customerOrderId,
                restaurantId,
                customerId,
                item,
                orderLimit,
                OrderState.CREATED,
                auditEntry
            )

        val orderCreatedEvent =
            OrderCreatedEvent(
                customerOrderId,
                restaurantId,
                customerId,
                item,
                orderLimit,
                OrderState.CREATED,
                customerOrderId,
                auditEntry
            )

        fixture.given(orderCreatedEvent)
            .`when`(createCustomerOrderCommand)
            .expectEvents(orderCreatedEvent)
    }
}

/*@Test
fun createCustomerOrderFailOrderLimitTest() {



}*/

/*   @Test
   fun markOrderAsDeliveredTest() {
       val customerOrderCreatedEvent = OrderCreatedEvent(orderTotal, customerId, orderId, auditEntry)
       val markCustomerOrderAsDeliveredCommand = MarkCustomerOrderAsDeliveredCommand(orderId, auditEntry)
       val customerOrderDeliveredEvent = CustomerOrderDeliveredEvent(orderId, auditEntry)

       fixture.given(customerOrderCreatedEvent)
           .`when`(markCustomerOrderAsDeliveredCommand)
           .expectEvents(customerOrderDeliveredEvent)

   }*/
