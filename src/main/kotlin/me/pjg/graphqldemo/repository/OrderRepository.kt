package me.pjg.graphqldemo.repository

import me.pjg.graphqldemo.model.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository : JpaRepository<Order, Int>, JpaSpecificationExecutor<Order> {
    @Query("select o from Order o where customerNumber = :customerNumber and (:orderNumber is null or orderNumber = :orderNumber)")
    fun findByCustomerNumberAndOrderNumber(customerNumber: Int, orderNumber: Int?) : List<Order>
}