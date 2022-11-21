package me.pjg.graphqldemo.model.entity

import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.Id

@Entity //Entity to get no-args constructor through jpa plugin
data class OrderDetailId(
    @Id val orderNumber: Int,
    @Id val productCode: String

) : Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }
}