package com.example.dripdropapp

//data class Product(
//    val name: String = "",
//    val price: Int = 0,
//    val rating: Double = 0.0,
//    val image: String = "",
//    val sizes: List<String> = listOf("S", "M", "L", "XL")
//)




data class Product(
    var image: String = "",
    var name: String = "",
    var price: Int = 0,
    var rating: Double = 0.0
)

//{
//    lateinit var image: Any
//}


//orgg
//data class CartItem(
//    var key: String? = null,
//    val productId: String = "",
//    val size: String = "",
//    var quantity: Int = 1,
//    val timestamp: Long = System.currentTimeMillis()
//)

//data class CartItem(
//    var key: String? = null,
//    val productId: String = "",
//    val category: String = "", // This is crucial for your structure
//    val size: String = "",
//    var quantity: Int = 1,
//    val timestamp: Long = System.currentTimeMillis(),
//    val productCategory: String
//)


//data class CartItem(
//    val productId: String = "",
//    val productCategory: String = "",
//    val size: String = "N/A",
//    val quantity: Int = 1
//)


//data class CartItem(
//    var productId: String = "",
//    var category: String = "",  // <- ensure this is included
//    var size: String = "M",     // or "N/A" if not applicable
//    var quantity: Int = 1,
//    @get:Exclude var key: String? = null  // for Firebase push key
//)

//package com.example.dripdropapp.models

data class CartItem(
    var productId: String = "",
    var category: String = "",
    var size: String = "M", // or "N/A" for shoes etc.
    var quantity: Int = 1,
    @Transient var key: String? = null // Firebase push key (ignored in upload)
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

