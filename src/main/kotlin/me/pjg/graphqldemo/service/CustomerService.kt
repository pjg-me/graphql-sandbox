package me.pjg.graphqldemo.service

import me.pjg.graphqldemo.model.graphql.Customer
import me.pjg.graphqldemo.model.graphql.SalesRepresentative
import me.pjg.graphqldemo.repository.CustomerRepository
import me.pjg.graphqldemo.repository.EmployeeRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.toList

@Service
class CustomerService(
    val customerRepository: CustomerRepository,
    val employeeRepository: EmployeeRepository
) {

    @OptIn(ExperimentalStdlibApi::class)
    fun fetchCustomers(customerNumber: Int?): List<Customer> {
        val results: List<me.pjg.graphqldemo.model.entity.Customer> = when (customerNumber) {
            null -> {
                customerRepository.findAll()
            }
            else -> {
                customerRepository.findById(customerNumber).toList()
            }
        }

        return results.map { Customer.fromEntity(it) }
    }

    fun fetchSalesRepresentativesForCustomers(customers: List<Customer>): Map<Customer, SalesRepresentative?> {
        val uniqueEmployeeNumber = customers.map { it.salesRepEmployeeNumber }.toSet()
        val salesRepresentativeById =
            employeeRepository.findAllById(uniqueEmployeeNumber)
                .filterNotNull()
                .associateBy { it.employeeNumber }
                .mapValues { entry -> SalesRepresentative.fromEntity(entry.value) }

        return customers.associateWith { salesRepresentativeById[it.salesRepEmployeeNumber] }
    }
}

