package me.pjg.graphqldemo.model.graphql

import me.pjg.graphqldemo.model.entity.Employee

data class SalesRepresentative(
    val employeeNumber: Int,
    val firstName: String,
    val lastName: String,
    val extension: String,
    val email: String,
    val jobTitle: String
) {
    companion object {
        fun fromEntity(employee: Employee): SalesRepresentative {
            return SalesRepresentative(
                employee.employeeNumber,
                employee.firstName,
                employee.lastName,
                employee.extension,
                employee.email,
                employee.jobTitle
            )
        }
    }
}