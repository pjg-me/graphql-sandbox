package me.pjg.graphqldemo.model.entity

import org.hibernate.Hibernate
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "payments", catalog = "classicmodels")
@IdClass(PaymentId::class)
data class Payment(
    @Id
    @Column(name = "customerNumber", nullable = false)
    private val customerNumber: Int,

    @Id
    @Column(name = "checkNumber", nullable = false, length = 50)
    private val checkNumber: String,

    @Column(name = "paymentDate", nullable = false)
    private val paymentDate: LocalDate,

    @Column(name = "amount", nullable = false)
    private val amount: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customerNumber", referencedColumnName = "customerNumber", insertable = false, updatable = false)
    private val customer: Customer? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Payment

        return customerNumber == other.customerNumber
                && checkNumber == other.checkNumber
    }

    override fun hashCode(): Int = Objects.hash(customerNumber, checkNumber)

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(customerNumber = $customerNumber , checkNumber = $checkNumber , paymentDate = $paymentDate , amount = $amount , customer = $customer )"
    }
}