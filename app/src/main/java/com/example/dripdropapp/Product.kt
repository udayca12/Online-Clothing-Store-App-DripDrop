package com.example.dripdropapp

data class Product(
    val name: String = "",
    val price: Int = 0,
    val rating: Double = 0.0,
    val image: String = "",
    val sizes: List<String> = listOf("S", "M", "L", "XL")
)
//{
//    lateinit var image: Any
//}

data class CartItem(
    var key: String? = null,
    val productId: String = "",
    val size: String = "",
    var quantity: Int = 1,
    val timestamp: Long = System.currentTimeMillis()
)


//data class CartItem(
//    val productId: String = "",
//    val size: String = "",
//    val quantity: Int = 1,
//
//)

data class Order(
    val productId: String = "",
    val size: String = "",
    val quantity: Int = 1,
    val orderTime: Long = System.currentTimeMillis()
)

