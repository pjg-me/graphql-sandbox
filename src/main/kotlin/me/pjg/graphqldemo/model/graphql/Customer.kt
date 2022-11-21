package me.pjg.graphqldemo.model.graphql

data class Customer(
    val addressLine1: String,
    val addressLine2: String? = null,
    val city: String,
    val contactFirstName: String,
    val contactLastName: String,
    val country: String,
    val creditLimit: Double? = null,
    val customerName: String,
    val customerNumber: Int,
    val phone: String,
    val postalCode: String? = null,
    val state: String? = null,
    val salesRepEmployeeNumber: Int? = null,
) {
    companion object {
        fun fromEntity(it: me.pjg.graphqldemo.model.entity.Customer) : Customer {
            return Customer(
                addressLine1 = it.addressLine1,
                addressLine2 = it.addressLine2,
                city = it.city,
                contactFirstName = it.contactFirstName,
                contactLastName = it.contactLastName,
                country = it.country,
                creditLimit = it.creditLimit,
                customerName = it.customerName,
                customerNumber = it.customerNumber,
                phone = it.phone,
                postalCode = it.postalCode,
                state = it.state,
                salesRepEmployeeNumber = it.salesRepEmployeeNumber
            )
        }
    }
}