package me.pjg.graphqldemo.model.graphql

data class Order(
    val comments: String? = null,
    val orderDate: String,
    val orderNumber: Int,
    val requiredDate: String,
    val shippedDate: String? = null,
    val status: String,
    val products: List<OrderProduct>,
    val orderTotal: Double
) {
    companion object {
        fun fromEntity(it: me.pjg.graphqldemo.model.entity.Order): Order {
            return Order(
                comments = it.comments,
                orderDate = it.orderDate.toString(),
                orderNumber = it.orderNumber,
                requiredDate = it.requiredDate.toString(),
                shippedDate = it.shippedDate.toString(),
                status = it.status,
                products = it.orderDetailsList.map(OrderProduct.Companion::fromEntity),
                orderTotal = it.orderDetailsList.sumOf { it.priceEach * it.quantityOrdered }
            )
        }
    }
}