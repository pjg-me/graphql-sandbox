package me.pjg.graphqldemo.controller

import me.pjg.graphqldemo.model.graphql.Customer
import me.pjg.graphqldemo.model.graphql.Order
import me.pjg.graphqldemo.model.graphql.SalesRepresentative
import me.pjg.graphqldemo.service.CustomerService
import me.pjg.graphqldemo.service.OrderService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class GraphQLController(
    val customerService: CustomerService,
    val orderService: OrderService
) {

    @QueryMapping
    fun customers(@Argument customerNumber: Int?): List<Customer?> {
        return customerService.fetchCustomers(customerNumber)
    }

    @QueryMapping
    fun orders(): List<Order?> {
        return orderService.fetchOrders()
    }

    @SchemaMapping(typeName = "Customer", field = "orders")
    fun fetchOrdersForCustomer(
        customer: Customer,
        @Argument orderNumber: Int?,
        @Argument highestValue: Boolean? = false
    ): List<Order> {
        val orders = orderService.fetchOrdersByCustomerAndOrderNumber(customer, orderNumber)

        return if (highestValue == true && orders.size > 1)
            listOf(orders.maxBy { it.orderTotal })
        else {
            orders
        }
    }

    @BatchMapping(typeName = "Customer", field = "salesRepresentative")
    fun fetchSalesRepresentativesForCustomers(customers: List<Customer>): Map<Customer, SalesRepresentative?> {
        return customerService.fetchSalesRepresentativesForCustomers(customers)
    }
}