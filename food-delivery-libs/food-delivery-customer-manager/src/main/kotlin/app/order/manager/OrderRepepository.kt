import app.model.ItemEntity
import app.model.OrderEntity
import app.model.ProductEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : CrudRepository<OrderEntity, String>

@Repository
interface ItemRepository : CrudRepository<ItemEntity, String>

@Repository
interface ProductRepository : CrudRepository<ProductEntity, String>
