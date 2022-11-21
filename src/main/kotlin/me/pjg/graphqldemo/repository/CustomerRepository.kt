package me.pjg.graphqldemo.repository

import me.pjg.graphqldemo.model.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : JpaRepository<Customer, Int>, JpaSpecificationExecutor<Customer> {
//    fun findAllByCustomerNumberIn(customerNumbers: Collection<Int?>): List<Customer>
}
