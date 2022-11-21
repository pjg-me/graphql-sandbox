package me.pjg.graphqldemo.model.graphql

import me.pjg.graphqldemo.model.entity.OrderDetail

data class OrderProduct (
    val productCode: String,
    val priceEach: Double,
    val quantityOrdered: Int,
    val productName: String,
    val productVendor: String,
    val productDescription: String
) {
    companion object{
        fun fromEntity(orderDetail: OrderDetail) : OrderProduct {
            return OrderProduct(
                productCode = orderDetail.productCode,
                priceEach = orderDetail.priceEach,
                quantityOrdered = orderDetail.quantityOrdered,
                productName = orderDetail.product.productName,
                productVendor = orderDetail.product.productVendor,
                productDescription = orderDetail.product.productDescription
            )
        }
    }
}