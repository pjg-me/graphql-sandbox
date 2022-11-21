package me.pjg.graphqldemo.model.entity

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "customers", catalog = "classicmodels")
data class Customer(
    @Id
    @Column(name = "customerNumber", nullable = false)
    val customerNumber: Int = 0,

    @Column(name = "customerName", nullable = false, length = 50)
    val customerName: String,

    @Column(name = "contactLastName", nullable = false, length = 50)
    val contactLastName: String,

    @Column(name = "contactFirstName", nullable = false, length = 50)
    val contactFirstName: String,

    @Column(name = "phone", nullable = false, length = 50)
    val phone: String,

    @Column(name = "addressLine1", nullable = false, length = 50)
    val addressLine1: String,

    @Column(name = "addressLine2", length = 50)
    val addressLine2: String? = null,

    @Column(name = "city", nullable = false, length = 50)
    val city: String,

    @Column(name = "state", length = 50)
    val state: String? = null,

    @Column(name = "postalCode", length = 15)
    val postalCode: String? = null,

    @Column(name = "country", nullable = false, length = 50)
    val country: String,

    @Column(name = "salesRepEmployeeNumber")
    val salesRepEmployeeNumber: Int? = null,

    @Column(name = "creditLimit")
    val creditLimit: Double? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "salesRepEmployeeNumber",
        referencedColumnName = "employeeNumber",
        insertable = false,
        updatable = false
    )
    val employee: Employee? = null,

    @OneToMany(mappedBy = "customer")
    val orderList: List<Order>? = null,

    @OneToMany(mappedBy = "customer")
    val paymentList: List<Payment>? = null

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Customer

        return customerNumber == other.customerNumber
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(customerNumber = $customerNumber , customerName = $customerName , contactLastName = $contactLastName , contactFirstName = $contactFirstName , phone = $phone , addressLine1 = $addressLine1 , addressLine2 = $addressLine2 , city = $city , state = $state , postalCode = $postalCode , country = $country , salesRepEmployeeNumber = $salesRepEmployeeNumber , creditLimit = $creditLimit )"
    }
}