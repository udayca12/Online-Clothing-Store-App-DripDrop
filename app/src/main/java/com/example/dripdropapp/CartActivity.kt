package com.example.dripdropapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.example.dripdropapp.Product


class CartActivity : AppCompatActivity() {

    private lateinit var cartItemsContainer: LinearLayout
    private lateinit var tvTotal: TextView
    private lateinit var btnCheckout: Button

    private val categories = listOf(
        "tshirts", "Jackets", "Jeans", "Shoes",
        "WomensDress", "WomensJeans", "WomensTop", "Heels"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_item)

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

        FirebaseDatabase.getInstance().getReference("carts")
            .child(userId)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    cartItemsContainer.removeAllViews()
                    val cartList = mutableListOf<CartItem>()

                    for (itemSnap in snapshot.children) {
                        val cartItem = itemSnap.getValue(CartItem::class.java)
                        cartItem?.key = itemSnap.key
                        cartItem?.let { cartList.add(it) }
                    }

                    if (cartList.isEmpty()) {
                        tvTotal.text = "Total: ₹0"
                        showEmptyCart()
                        return
                    }

                    fetchAllCartProducts(cartList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@CartActivity, "Failed to load cart", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun fetchAllCartProducts(cartList: List<CartItem>) {
        var totalPrice = 0
        var fetchedCount = 0

        for (cartItem in cartList) {
            fetchProductDetails(cartItem) { product ->
                fetchedCount++
                if (product != null) {
                    addCartItemCard(cartItem, product)
                    totalPrice += product.price * cartItem.quantity
                }

                if (fetchedCount == cartList.size) {
                    tvTotal.text = "Total: ₹$totalPrice"
                }
            }
        }
    }

    private fun fetchProductDetails(cartItem: CartItem, callback: (Product?) -> Unit) {
        if (cartItem.category.isNotEmpty()) {
            FirebaseDatabase.getInstance().getReference("products")
                .child(cartItem.category)
                .child(cartItem.productId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val product = snapshot.getValue(Product::class.java)
                        callback(product)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback(null)
                    }
                })
        } else {
            // fallback to search all categories if category is missing
            searchAllCategories(cartItem, callback)
        }
    }

    private fun searchAllCategories(cartItem: CartItem, callback: (Product?) -> Unit) {
        var found = false
        var searched = 0

        for (category in categories) {
            FirebaseDatabase.getInstance().getReference("products")
                .child(category)
                .child(cartItem.productId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        searched++
                        if (snapshot.exists() && !found) {
                            found = true
                            val product = snapshot.getValue(Product::class.java)
                            callback(product)
                        } else if (searched == categories.size && !found) {
                            callback(null)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        searched++
                        if (searched == categories.size && !found) {
                            callback(null)
                        }
                    }
                })
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun addCartItemCard(cartItem: CartItem, product: Product) {
        val view = LayoutInflater.from(this).inflate(R.layout.cart_item_card, cartItemsContainer, false)

        val ivProductImage = view.findViewById<ImageView>(R.id.ivProductImage)
        val tvProductName = view.findViewById<TextView>(R.id.tvProductName)
        val tvProductSize = view.findViewById<TextView>(R.id.tvProductSize)
        val tvProductPrice = view.findViewById<TextView>(R.id.tvProductPrice)
        val btnRemove = view.findViewById<Button>(R.id.btnRemove)
        val btnMinus = view.findViewById<Button>(R.id.btnMinus)
        val btnPlus = view.findViewById<Button>(R.id.btnPlus)
        val tvQuantity = view.findViewById<TextView>(R.id.tvQuantity)

        Glide.with(this)
            .load(product.image)
            .placeholder(R.drawable.blury_background)
            .error(R.drawable.blury_background)
            .into(ivProductImage)

        tvProductName.text = product.name
        tvProductSize.text = "Size: ${cartItem.size}"
        tvProductPrice.text = "₹${product.price * cartItem.quantity}"
        tvQuantity.text = cartItem.quantity.toString()

        btnMinus.setOnClickListener {
            if (cartItem.quantity > 1) {
                updateCartItemQuantity(cartItem, cartItem.quantity - 1)
            } else {
                removeCartItem(cartItem)
            }
        }

        btnPlus.setOnClickListener {
            updateCartItemQuantity(cartItem, cartItem.quantity + 1)
        }

        btnRemove.setOnClickListener {
            removeCartItem(cartItem)
        }

        cartItemsContainer.addView(view)
    }

    private fun updateCartItemQuantity(item: CartItem, newQty: Int) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val itemKey = item.key ?: return

        FirebaseDatabase.getInstance().getReference("carts")
            .child(userId)
            .child(itemKey)
            .child("quantity")
            .setValue(newQty)
    }

    private fun removeCartItem(item: CartItem) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val itemKey = item.key ?: return

        FirebaseDatabase.getInstance().getReference("carts")
            .child(userId)
            .child(itemKey)
            .removeValue()
    }

    private fun showEmptyCart() {
        val emptyText = TextView(this).apply {
            text = "Your cart is empty"
            textSize = 18f
            gravity = android.view.Gravity.CENTER
            setPadding(50, 100, 50, 100)
        }
        cartItemsContainer.addView(emptyText)
    }
}
