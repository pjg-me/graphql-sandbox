package me.pjg.graphqldemo.model.entity

import org.hibernate.Hibernate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "employees", catalog = "classicmodels")
data class Employee(
    @Id
    @Column(name = "employeeNumber", nullable = false)
    val employeeNumber: Int = 0,

    @Column(name = "lastName", nullable = false, length = 50)
    val lastName: String,

    @Column(name = "firstName", nullable = false, length = 50)
    val firstName: String,

    @Column(name = "extension", nullable = false, length = 10)
    val extension: String,

    @Column(name = "email", nullable = false, length = 100)
    val email: String,

    @Column(name = "officeCode", nullable = false, length = 10)
    val officeCode: String,

    @Column(name = "reportsTo")
    val reportsTo: Int? = null,

    @Column(name = "jobTitle", nullable = false, length = 50)
    val jobTitle: String,

    @OneToMany(mappedBy = "employee")
    val customerList: List<Customer>? = null,

    @ManyToOne
    @JoinColumn(name = "reportsTo", referencedColumnName = "employeeNumber", insertable = false, updatable = false)
    val employee: Employee? = null,

    @OneToMany(mappedBy = "employee")
    val employeeList: List<Employee>? = null,

    @ManyToOne
    @JoinColumn(name = "officeCode", referencedColumnName = "officeCode", insertable = false, updatable = false)
    val office: Office? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Employee

        return employeeNumber == other.employeeNumber
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(employeeNumber = $employeeNumber , lastName = $lastName , firstName = $firstName , extension = $extension , email = $email , officeCode = $officeCode , reportsTo = $reportsTo , jobTitle = $jobTitle , employee = $employee , office = $office )"
    }
}