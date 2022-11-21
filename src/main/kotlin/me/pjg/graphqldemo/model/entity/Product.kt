package me.pjg.graphqldemo.model.entity

import org.hibernate.Hibernate
import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "products", catalog = "classicmodels")
data class Product(
    @Id
    @Column(name = "productCode", nullable = false, length = 15)
    val productCode: String,

    @Column(name = "productName", nullable = false, length = 70)
    val productName: String,

    @Column(name = "productLine", nullable = false, length = 50)
    val productLine: String,

    @Column(name = "productScale", nullable = false, length = 10)
    val productScale: String,

    @Column(name = "productVendor", nullable = false, length = 50)
    val productVendor: String,

    @Column(name = "productDescription", nullable = false, length = 65535)
    val productDescription: String,

    @Column(name = "quantityInStock", nullable = false)
    val quantityInStock: Short,

    @Column(name = "buyPrice", nullable = false)
    val buyPrice: BigDecimal,

    @Column(name = "MSRP", nullable = false)
    val msrp: BigDecimal,

    @OneToMany(mappedBy = "product")
    val orderDetailList: List<OrderDetail>? = null,

    @ManyToOne
    @JoinColumn(name = "productLine", referencedColumnName = "productLine", insertable = false, updatable = false)
    val productlines: ProductLine? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Product

        return productCode == other.productCode
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(productCode = $productCode , productName = $productName , productLine = $productLine , productScale = $productScale , productVendor = $productVendor , productDescription = $productDescription , quantityInStock = $quantityInStock , buyPrice = $buyPrice , msrp = $msrp , productlines = $productlines )"
    }
}