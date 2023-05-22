package app.model

import core.api.order.OrderState
import core.api.restaurant.RestaurantState
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.math.BigDecimal

@Entity(name = "customer")
class CustomerEntity(
    @Id
    var id: String,
    var firstName: String,
    var lastName: String,
)

@Entity(name = "restaurant")
class RestaurantEntity(
    @Id
    var restaurantId: String,
    var name: String,
    var state: RestaurantState?,
)

@Entity(name = "purchase_order")
data class OrderEntity(
    @Id
    var orderId: String,
    var orderLimit: Int,
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    var restaurant: RestaurantEntity,
    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerEntity,
    @OneToMany
    @JoinColumn(name = "order_id")
    var items: List<ItemEntity>,
    @Enumerated
    var state: OrderState?

)

@Entity(name = "item")
class ItemEntity(
    @Id
    var itemId: String,
    var totalPrice: BigDecimal,
    @OneToMany
    @JoinColumn(name = "item_id")
    var products: List<ProductEntity>
)

@Entity(name = "product")
class ProductEntity(
    @Id
    var productId: String,
    var description: String,
    var price: BigDecimal
)