package me.pjg.graphqldemo.model.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity //Entity to get no-args constructor through jpa plugin
data class PaymentId(
    @Id val customerNumber: Int,
    @Id val checkNumber: String
) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}