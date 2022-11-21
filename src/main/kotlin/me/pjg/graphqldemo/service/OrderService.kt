package me.pjg.graphqldemo.service

import me.pjg.graphqldemo.model.graphql.Customer
import me.pjg.graphqldemo.model.graphql.Order
import me.pjg.graphqldemo.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(val orderRepository: OrderRepository) {

    fun fetchOrdersByCustomerAndOrderNumber(customer: Customer, orderNumber: Int?): List<Order> {
        return orderRepository.findByCustomerNumberAndOrderNumber(customer.customerNumber, orderNumber)
            .map(Order.Companion::fromEntity)
            .toList()
    }

    fun fetchOrders(): List<Order?> {
        return orderRepository.findAll()
            .map(Order.Companion::fromEntity)
            .toList()
    }
}

