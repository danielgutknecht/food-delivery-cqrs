package app.restaurant.manager

import app.model.RestaurantEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RestaurantRepository : CrudRepository<RestaurantEntity, String>
