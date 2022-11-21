package me.pjg.graphqldemo.model.entity

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "offices", catalog = "classicmodels")
data class Office (
    @Id
    @Column(name = "officeCode", nullable = false, length = 10)
    val officeCode: String,

    @Column(name = "city", nullable = false, length = 50)
    val city: String,

    @Column(name = "phone", nullable = false, length = 50)
    val phone: String,

    @Column(name = "addressLine1", nullable = false, length = 50)
    val addressLine1: String,

    @Column(name = "addressLine2", length = 50)
    val addressLine2: String? = null,

    @Column(name = "state", length = 50)
    val state: String? = null,

    @Column(name = "country", nullable = false, length = 50)
    val country: String,

    @Column(name = "postalCode", nullable = false, length = 15)
    val postalCode: String,

    @Column(name = "territory", nullable = false, length = 10)
    val territory: String,

    @OneToMany(mappedBy = "office")
    val employeeList: List<Employee>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Office

        return officeCode == other.officeCode
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(officeCode = $officeCode , city = $city , phone = $phone , addressLine1 = $addressLine1 , addressLine2 = $addressLine2 , state = $state , country = $country , postalCode = $postalCode , territory = $territory )"
    }
}