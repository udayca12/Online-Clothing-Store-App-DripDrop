package com.example.dripdropapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CartActivity : AppCompatActivity() {

    private lateinit var cartItemsContainer: LinearLayout
    private lateinit var tvTotal: TextView
    private lateinit var btnCheckout: Button
    private val categories = listOf("tshirts", "Jackets", "Jeans", "Shoes",
        "WomensDress", "WomensJeans", "WomensTop", "Heels")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_item) // Make sure this is your activity layout

        cartItemsContainer = findViewById(R.id.cartItemsContainer)
        tvTotal = findViewById(R.id.tvTotal)
        btnCheckout = findViewById(R.id.btnCheckout)

        loadCartItems()

        btnCheckout.setOnClickListener {
            Toast.makeText(this, "Checkout feature coming soon!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadCartItems() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseDatabase.getInstance().getReference("carts").child(userId)
            .addValueEventListener(object : ValueEventListener { // Use addValueEventListener for real-time update
                override fun onDataChange(snapshot: DataSnapshot) {
                    cartItemsContainer.removeAllViews()
                    var totalPrice = 0
                    val cartItems = mutableListOf<Pair<CartItem, Product>>()
                    val tasks = mutableListOf<DatabaseReference>()

                    val cartList = mutableListOf<CartItem>()
                    for (itemSnap in snapshot.children) {
                        val cartItem = itemSnap.getValue(CartItem::class.java)
                        cartItem?.key = itemSnap.key
                        cartItem?.let { cartList.add(it) }
                    }
                    if (cartList.isEmpty()) {
                        tvTotal.text = "Total: ₹0"
                        return
                    }
                    // For each cart item, fetch product details
                    var fetchedCount = 0
                    for (cartItem in cartList) {
                        fetchProductDetails(cartItem) { product ->
                            if (product != null) {
                                addCartItemCard(cartItem, product)
                                totalPrice += product.price * cartItem.quantity
                                tvTotal.text = "Total: ₹$totalPrice"
                            }
                            fetchedCount++
                            if (fetchedCount == cartList.size) {
                                // All items processed
                                tvTotal.text = "Total: ₹$totalPrice"
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CartActivity, "Failed to load cart", Toast.LENGTH_SHORT).show()
                }
            })
    }

    // Fetch product details and call callback with the product
    private fun fetchProductDetails(cartItem: CartItem, callback: (Product?) -> Unit) {
        // If you store category in cart, use it directly. Otherwise, search all categories.
        var found = false
        for (category in categories) {
            FirebaseDatabase.getInstance().getReference("products")
                .child(category).child(cartItem.productId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(productSnap: DataSnapshot) {
                        if (productSnap.exists() && !found) {
                            found = true
                            val product = productSnap.getValue(Product::class.java)
                            callback(product)
                        } else if (category == categories.last()) {
                            // Last category and not found
                            callback(null)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        if (category == categories.last()) callback(null)
                    }
                })
            if (found) break
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun addCartItemCard(cartItem: CartItem, product: Product) {
        val cardView = LayoutInflater.from(this).inflate(R.layout.cart_item_card, cartItemsContainer, false)

        val ivProductImage = cardView.findViewById<ImageView>(R.id.ivProductImage)
        val tvProductName = cardView.findViewById<TextView>(R.id.tvProductName)
        val tvProductSize = cardView.findViewById<TextView>(R.id.tvProductSize)
        val tvProductPrice = cardView.findViewById<TextView>(R.id.tvProductPrice)
        val btnRemove = cardView.findViewById<Button>(R.id.btnRemove)
        val btnMinus = cardView.findViewById<Button>(R.id.btnMinus)
        val btnPlus = cardView.findViewById<Button>(R.id.btnPlus)
        val tvQuantity = cardView.findViewById<TextView>(R.id.tvQuantity)

        Glide.with(this).load(product.image).into(ivProductImage)
        tvProductName.text = product.name
        tvProductSize.text = "Size: ${cartItem.size}"
        tvProductPrice.text = "₹${product.price * cartItem.quantity}"
        tvQuantity.text = cartItem.quantity.toString()

        btnMinus.setOnClickListener {
            if (cartItem.quantity > 1) {
                updateCartItemQuantity(cartItem, cartItem.quantity - 1)
            } else {
                // If quantity would become 0, remove the item
                removeCartItem(cartItem)
            }
        }

        btnPlus.setOnClickListener {
            updateCartItemQuantity(cartItem, cartItem.quantity + 1)
        }

        btnRemove.setOnClickListener {
            removeCartItem(cartItem)
        }

        cartItemsContainer.addView(cardView)
    }

    private fun updateCartItemQuantity(item: CartItem, newQuantity: Int) {
        if (item.key == null) return
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        if (newQuantity <= 0) {
            removeCartItem(item)
        } else {
            FirebaseDatabase.getInstance().getReference("carts")
                .child(userId)
                .child(item.key!!)
                .child("quantity").setValue(newQuantity)
        }
    }

    private fun removeCartItem(item: CartItem) {
        if (item.key == null) return
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseDatabase.getInstance().getReference("carts")
            .child(userId)
            .child(item.key!!).removeValue()
    }
}
