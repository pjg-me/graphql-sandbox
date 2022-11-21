package me.pjg.graphqldemo.model.entity

import org.hibernate.Hibernate
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.IdClass
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "orderdetails", catalog = "classicmodels")
@IdClass(
    OrderDetailId::class
)
data class OrderDetail(

    @Id
    @Column(name = "orderNumber", nullable = false)
    val orderNumber: Int,

    @Id
    @Column(name = "productCode", nullable = false, length = 15)
    val productCode: String,

    @Column(name = "quantityOrdered", nullable = false)
    val quantityOrdered: Int,

    @Column(name = "priceEach", nullable = false)
    val priceEach: Double,

    @Column(name = "orderLineNumber", nullable = false)
    val orderLineNumber: Short,

    @ManyToOne
    @JoinColumn(name = "orderNumber", referencedColumnName = "orderNumber", insertable = false, updatable = false)
    val order: Order? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "productCode", referencedColumnName = "productCode", insertable = false, updatable = false)
    val product: Product
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as OrderDetail

        return orderNumber == other.orderNumber
                && productCode == other.productCode
    }

    override fun hashCode(): Int = Objects.hash(orderNumber, productCode)

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(orderNumber = $orderNumber , productCode = $productCode , quantityOrdered = $quantityOrdered , priceEach = $priceEach , orderLineNumber = $orderLineNumber , order = $order , product = $product )"
    }

}