package me.pjg.graphqldemo.model.entity

import org.hibernate.Hibernate
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.FetchType.LAZY
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "orders", catalog = "classicmodels")
data class Order(
    @Id
    @Column(name = "orderNumber", nullable = false)
    var orderNumber: Int,

    @Column(name = "orderDate", nullable = false)
    val orderDate: LocalDate,

    @Column(name = "requiredDate", nullable = false)
    val requiredDate: LocalDate,

    @Column(name = "shippedDate")
    val shippedDate: LocalDate? = null,

    @Column(name = "status", nullable = false, length = 15)
    val status: String,

    @Column(name = "comments", length = 65535)
    val comments: String? = null,

    @Column(name = "customerNumber", nullable = false)
    var customerNumber: Int,

    @OneToMany(mappedBy = "order", fetch = EAGER)
    val orderDetailsList: List<OrderDetail>,

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "customerNumber", referencedColumnName = "customerNumber", insertable = false, updatable = false)
    val customer: Customer? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Order

        return orderNumber == other.orderNumber
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(orderNumber = $orderNumber , orderDate = $orderDate , requiredDate = $requiredDate , shippedDate = $shippedDate , status = $status , comments = $comments , customerNumber = $customerNumber )"
    }

}