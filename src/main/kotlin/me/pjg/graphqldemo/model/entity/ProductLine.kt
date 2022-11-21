package me.pjg.graphqldemo.model.entity

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "productlines", catalog = "classicmodels")
data class ProductLine (
    @Id
    @Column(name = "productLine", nullable = false, length = 50)
    private val productLine: String,

    @Column(name = "textDescription", length = 4000)
    private val textDescription: String? = null,

    @Column(name = "htmlDescription", length = 16777215)
    private val htmlDescription: String? = null,

    @Lob
    @Column(name = "image")
    private val image: ByteArray? = null,

    @OneToMany(mappedBy = "productLine")
    private val productList: List<Product>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ProductLine

        return productLine == other.productLine
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(productLine = $productLine , textDescription = $textDescription , htmlDescription = $htmlDescription , image = $image )"
    }
}